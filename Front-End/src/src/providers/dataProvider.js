import { stringify } from "query-string";
import {
  fetchUtils,
  GET_LIST,
  GET_ONE,
  GET_MANY,
  GET_MANY_REFERENCE,
  CREATE,
  UPDATE,
  UPDATE_MANY,
  DELETE,
  DELETE_MANY,
} from "react-admin";


function swtichResourceMethods(type, resource, params, apiUrl) {
  let url = "";
  const options = {};

  switch (type) {
    case GET_LIST: {
      let { page, perPage } = params.pagination;
      --page;
      url = `${apiUrl}/${resource}?page=${page}&pageSize=${perPage}&filterParams=${encodeURIComponent(
        JSON.stringify(params.filter)
      )}`;
      break;
    }
    case GET_ONE:
      url = `${apiUrl}/${resource}/${params.id}`;
      break;
    case GET_MANY: {
      const query = {
        filter: JSON.stringify({ id: params.ids }),
      };
      let idStr = "";
      const queryString = params.ids.map((id) => idStr + `id=${id}`);
      url = `${apiUrl}/${resource}/${queryString}`;
      break;
    }
    case GET_MANY_REFERENCE: {
      let { page, perPage } = params.pagination;
      --page;

      if (params.filter.method != undefined) {
        url = `${apiUrl}/${resource}/${params.filter.method}/${params.id}?page=${page}&pageSize=${perPage}`;
      } else {
        url = `${apiUrl}/${resource}?page=${page}&pageSize=${perPage}`;
      }
      // url = `${apiUrl}/${resource}/${params.filter.method}/${params.id}`;
      break;
    }
    case UPDATE:
      url = `${apiUrl}/${resource}/${params.id}`;
      options.method = "PUT";
      options.body = JSON.stringify(params.data);
      break;
    case CREATE:
      url = `${apiUrl}/${resource}`;
      options.method = "POST";
      options.body = JSON.stringify(params.data);
      break;
    case DELETE:
      url = `${apiUrl}/${resource}/${params.id}`;
      options.method = "DELETE";
      break;
    default:
      throw new Error(`Unsupported fetch action type ${type}`);
  }

  return { url, options };
}


export default (apiUrl, httpClient = fetchUtils.fetchJson) => {
  /**
   * @param {String} type One of the constants appearing at the top if this file, e.g. 'UPDATE'
   * @param {String} resource Name of the resource to fetch, e.g. 'posts'
   * @param {Object} params The data request params, depending on the type
   * @returns {Object} { url, options } The HTTP request parameters
   */
 
  const convertDataRequestToHTTP = (type, resource, params) => {
   
    return swtichResourceMethods(type, resource, params, apiUrl);
  };

  /**
   * @param {Object} response HTTP response from fetch()
   * @param {String} type One of the constants appearing at the top if this file, e.g. 'UPDATE'
   * @param {String} resource Name of the resource to fetch, e.g. 'posts'
   * @param {Object} params The data request params, depending on the type
   * @returns {Object} Data response
   */
  const convertHTTPResponse = (response, type, resource, params) => {
    const { headers, json } = response;
    switch (type) {
      case GET_LIST:
      case GET_MANY_REFERENCE:
        if (!json.hasOwnProperty("totalElements")) {
          throw new Error(
            "The numberOfElements property must be must be present in the Json response"
          );
        }
        return {
          data: json.content,
          total: parseInt(json.totalElements, 10),
        };
      case CREATE:
        return { data: { ...params.data, id: json.id } };
      default:
        return { data: json };
    }
  };

  /**
   * @param {string} type Request type, e.g GET_LIST
   * @param {string} resource Resource name, e.g. "posts"
   * @param {Object} payload Request parameters. Depends on the request type
   * @returns {Promise} the Promise for a data response
   */
  return (type, resource, params) => {

    // simple-rest doesn't handle filters on UPDATE route, so we fallback to calling UPDATE n times instead
    if (type === UPDATE_MANY) {
      return Promise.all(
        params.ids.map((id) =>
          httpClient(`${apiUrl}/${resource}/${id}`, {
            method: "PUT",
            body: JSON.stringify(params.data),
          })
        )
      ).then((responses) => ({
        data: responses.map((response) => response.json),
      }));
    }
    // simple-rest doesn't handle filters on DELETE route, so we fallback to calling DELETE n times instead
    if (type === DELETE_MANY) {
      return Promise.all(
        params.ids.map((id) =>
          httpClient(`${apiUrl}/${resource}/${id}`, {
            method: "DELETE",
          })
        )
      ).then((responses) => ({
        data: responses.map((response) => response.json),
      }));
    }

    const { url, options } = convertDataRequestToHTTP(type, resource, params);
    
    return httpClient(url, options).then((response) => {
      return convertHTTPResponse(response, type, resource, params);
    });
  };
};
