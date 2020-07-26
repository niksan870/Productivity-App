import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import compose from "recompose/compose";
import SettingsIcon from "@material-ui/icons/Settings";
import { withRouter } from "react-router-dom";
import MusicNoteIcon from "@material-ui/icons/MusicNote";
import ScheduleIcon from "@material-ui/icons/Schedule";
import ListIcon from "@material-ui/icons/List";
import PeopleIcon from "@material-ui/icons/People";
import {
  translate,
  DashboardMenuItem,
  MenuItemLink,
  useTranslate,
} from "react-admin";

import SubMenu from "./SubMenu";

class Menu extends Component {
  state = {
    menuCatalog: false,
    pomodoroSettings: false,
    menuCustomers: false,
  };

  static propTypes = {
    onMenuClick: PropTypes.func,
    logout: PropTypes.object,
  };

  handleToggle = (menu) => {
    this.setState((state) => ({ [menu]: !state[menu] }));
  };

  render() {
    let permissions = localStorage.getItem("permissions");
    const { onMenuClick, open, logout, translate } = this.props;
    return (
      <div>
        {permissions != null
          ? [
              <DashboardMenuItem onClick={onMenuClick} />,
              <MenuItemLink
                to={`/profiles`}
                primaryText="User Profiles"
                onClick={onMenuClick}
                leftIcon={<PeopleIcon />}
              />,
              <MenuItemLink
                to={`/goals`}
                primaryText="Goals"
                onClick={onMenuClick}
                leftIcon={<ListIcon />}
              />,
              <SubMenu
                handleToggle={() => this.handleToggle("pomodoroSettings")}
                isOpen={this.state.pomodoroSettings}
                sidebarIsOpen={open}
                leftIcon={<SettingsIcon />}
                name="ra.page.settings"
              >
                <MenuItemLink
                  to={`/pomodoros`}
                  primaryText="Pomodoros"
                  leftIcon={<ScheduleIcon />}
                  onClick={onMenuClick}
                />
                <MenuItemLink
                  to={`/music`}
                  leftIcon={<MusicNoteIcon />}
                  primaryText="Music"
                  onClick={onMenuClick}
                />
              </SubMenu>,
            ]
          : null}
        {permissions.includes("ROLE_ADMIN") ? (
          <MenuItemLink
            to={`/users`}
            primaryText="Users"
            onClick={onMenuClick}
            leftIcon={<PeopleIcon />}
          />
        ) : null}
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  open: state.admin.ui.sidebarOpen,
  theme: state.theme,
});

const enhance = compose(withRouter, connect(mapStateToProps, {}), translate);

export default enhance(Menu);
