import React from "react";
import { createHashHistory } from "history";
import {
  UserList,
  ProfilesList,
  GoalsList,
  PomodoroList,
  MusicList,
} from "./src/lists/lists";
import AccessTimeIcon from "@material-ui/icons/AccessTime";
import PeopleIcon from "@material-ui/icons/People";
import Dashboard from "./src/components/Dashboard";
import authProvider from "./src/providers/authProvider";
import { BASE_API_URL } from "./constants";
import customRoutes from "./src/customRoutes";
import LoginPage from "./src/components/register/App";
import { Route } from 'react-router-dom';
import MyLayout from "./src/layout/MyLayout";
import { fetchUtils, Admin, Resource, EditGuesser } from "react-admin";
import provider from "./src/providers/dataProvider";
import { ProfileEdit, ProfileShow } from "./src/components/profile";
import { UserEdit, UserShow } from "./src/components/user";
import { GoalEdit, GoalShow, GoalCreate } from "./src/components/goal";
import ListIcon from "@material-ui/icons/List";
import MusicNoteIcon from "@material-ui/icons/MusicNote";
import { Provider } from "react-redux";
import addUploadFeature from "./src/providers/addUploadFeature";
import createAdminStore from "./src/createAdminStore";
import {
  PomodoroEdit,
  PomodoroShow,
  PomodoroCreate,
} from "./src/components/pomodoro";
import { MusicEdit, MusicShow, MusicCreate } from "./src/components/music";
import Menu from "./src/menu/menu";
import polyglotI18nProvider from "ra-i18n-polyglot";
import { englishMessages } from "./src/translate/translate";
import profile from './src/profile/index';
import { GET_ONE, UPDATE } from "react-admin";


// A function decorating a dataProvider for handling user profiles
const handleUserProfile = dataProvider => (verb, resource, params) => {
  // I know I only GET or UPDATE the profile as there is only one for the current user
  // To showcase how I can do something completely different here, I'll store it in local storage
  // You can replace this with a customized fetch call to your own API route, too
  if (resource === "profile") {
    if (verb === GET_ONE) {
      let storedProfile = localStorage.getItem("my-profile");

      if (storedProfile) {
        return Promise.resolve({
          data: JSON.parse(storedProfile),
        });
      }
   

      return Promise.resolve(
        httpClient(`${BASE_API_URL}/profiles/me`, {
          method: "GET",
        })
      ).then((response) => {
        response.json["id"] = "my-profile";
        console.log(response.json)
        localStorage.setItem("my-profile", JSON.stringify(response.json));
        return ({
          data: response.json,
        })
      });
    }

    if (verb === UPDATE) {
      localStorage.setItem("profile", JSON.stringify(params.data));
      return Promise.resolve({ data: params.data });
    }
  }

  // Fallback to the dataProvider default handling for all other resources
  return dataProvider(verb, resource, params);
};

const httpClient = (url, options = {}) => {
  if (!options.headers) {
    options.headers = new Headers({ Accept: "application/json" });
  }
  const token = localStorage.getItem("accessToken");
  options.headers.set("Authorization", `Bearer ${token}`);
  return fetchUtils.fetchJson(url, options);
};
const dataProvider = provider(BASE_API_URL, httpClient);
const uploadCapableDataProvider = addUploadFeature(dataProvider);
const testProvider = handleUserProfile(
  uploadCapableDataProvider
);

const history = createHashHistory();
const i18nProvider = polyglotI18nProvider((locale) => englishMessages);

const App = () => {
  return (
    <Provider
      store={createAdminStore({
        authProvider,
        testProvider,
        history,
      })}
    >
      <Admin
        locale="en"
        menu={Menu}
        appLayout={MyLayout}
        dashboard={Dashboard}
        loginPage={LoginPage}
        authProvider={authProvider}
        dataProvider={testProvider}
        i18nProvider={i18nProvider}
        customRoutes={[
          <Route
            key="my-profile"
            path="/my-profile"
            component={profile.show}
          />
        ]}
       history={history}
      >
        {(permissions) => {
          return [
            <Resource 
            name="profile"
            edit={profile.edit} 
            show={profile.show} 
            />,
            <Resource
              name="profiles"
              list={ProfilesList}
              show={ProfileShow}
              edit={ProfileEdit}
              icon={PeopleIcon}
            />,
            <Resource
              name="goals"
              show={GoalShow}
              create={GoalCreate}
              edit={GoalEdit}
              list={GoalsList}
              icon={ListIcon}
            />,
            <Resource
              name="pomodoros"
              show={PomodoroShow}
              create={PomodoroCreate}
              edit={PomodoroEdit}
              list={PomodoroList}
              icon={AccessTimeIcon}
            />,
            <Resource
              name="music"
              show={MusicShow}
              create={MusicCreate}
              edit={MusicEdit}
              list={MusicList}
              icon={MusicNoteIcon}
            />,
            permissions.includes("ROLE_ADMIN") ? (
              <Resource
                name="users"
                list={UserList}
                icon={PeopleIcon}
                show={UserShow}
                edit={UserEdit}
              />
            ) : null,
          ];
        }}
      </Admin>
    </Provider>
  );
};

export default App;
