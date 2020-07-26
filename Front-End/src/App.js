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

import MyLayout from "./src/layout/MyLayout";

import { fetchUtils, Admin, Resource } from "react-admin";
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
import bitcoinRateReducer from "./src/reducers/bitcoinRateReducer";
import polyglotI18nProvider from "ra-i18n-polyglot";
import { englishMessages } from "./src/translate/translate";

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

const history = createHashHistory();

const i18nProvider = polyglotI18nProvider((locale) => englishMessages);

const App = () => {
  return (
    <Provider
      store={createAdminStore({
        authProvider,
        dataProvider,
        history,
      })}
    >
      <Admin
        locale="en"
        menu={Menu}
        customRoutes={customRoutes}
        loginPage={LoginPage}
        history={history}
        appLayout={MyLayout}
        authProvider={authProvider}
        dataProvider={uploadCapableDataProvider}
        dashboard={Dashboard}
        i18nProvider={i18nProvider}
      >
        {(permissions) => {
          return [
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
