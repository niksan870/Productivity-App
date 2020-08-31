// in src/MyUserMenu.js
import React, { Component } from 'react';
import { connect } from 'react-redux';
import Typography from '@material-ui/core/Typography';
import { crudGetOne, UserMenu, MenuItemLink } from 'react-admin';
import SettingsIcon from '@material-ui/icons/Settings';

class MyUserMenuView extends Component {
    componentDidMount() {
        this.fetchProfile();
    }

    fetchProfile = () => {
        this.props.crudGetOne(
            // The resource
            'profile',
            // The id of the resource item to fetch
            'my-profile',
            // The base path. Mainly used on failure to fetch the data
            '/my-profile',
            // Whether to refresh the current view. I don't need it here
            false
        );
    };

    render() {
        const { crudGetOne, profile, ...props } = this.props;

        console.log(this.props)
        return (
            <UserMenu label={profile ? profile.nickname : ''} {...props}>
                <MenuItemLink
                    to="/configuration"
                    primaryText="Configuration"
                    leftIcon={<SettingsIcon />}
                />
            </UserMenu>
        );
    }
}

const mapStateToProps = state => {
    const resource = 'profile';
    const id = 'my-profile';
    const profileState = state.admin.resources[resource];

    return {
        profile: profileState ? profileState.data[id] : null
    };
};

const MyUserMenu = connect(
    mapStateToProps,
    { crudGetOne }
)(MyUserMenuView);
export default MyUserMenu;