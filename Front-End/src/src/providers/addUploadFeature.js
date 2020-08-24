const convertFileToBase64 = (file) =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file.rawFile);

    reader.onload = () => resolve(reader.result);
    reader.onerror = reject;
  });

const addUploadFeature = (requestHandler) => (type, resource, params) => {
  if (type === "UPDATE" && resource === "profiles") {
    console.log(params.data);
    const newPicture = params.data.picture;
    if (typeof newPicture == "string") {
      return Promise.all([newPicture]).then((transformedNewPicture) => {
        return requestHandler(type, resource, {
          ...params,
          data: {
            ...params.data,
            picture: transformedNewPicture[0],
          },
        });
      });
    } else {
      return Promise.all([convertFileToBase64(newPicture)]).then(
        (transformedNewPicture) => {
          return requestHandler(type, resource, {
            ...params,
            data: {
              ...params.data,
              picture: transformedNewPicture[0],
            },
          });
        }
      );
    }
  }
  return requestHandler(type, resource, params);
};

export default addUploadFeature;
