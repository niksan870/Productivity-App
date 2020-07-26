import React from "react";
import {
  List,
  TextField,
  EmailField,
  ChipField,
  EditButton,
  DeleteButton,
  ShowButton,
  Datagrid,
} from "react-admin";

function ifAdminGetField(permissions, string, props = null) {
  if (permissions !== undefined) {
    if (permissions.includes("ROLE_ADMIN")) {
      if (string === "delete") {
        return <DeleteButton />;
      } else if (string === "edit") {
        return <EditButton />;
      } else {
        return <TextField source={string} />;
      }
    }
  }
}

const UserProfileHref = ({ record = {}, source }) => {
  return (
    <a href={"#/profiles/" + record.userProfile.id + "/show"}>
      {record.userProfile.name}
    </a>
  );
};

export const UserList = (props, permissions) => {
  return (
    <List {...props}>
      <Datagrid>
        {ifAdminGetField(props.permissions, "id")}
        <UserProfileHref source="userProfile" />
        <TextField source="username" />
        <TextField source="permissionList" />
        <ChipField source="roles" />
        <EmailField source="email" />
        <TextField source="active" />
        {ifAdminGetField(props.permissions, "createdAt")}
        {ifAdminGetField(props.permissions, "updatedAt")}
        <ShowButton />
        {ifAdminGetField(props.permissions, "edit")}
        {ifAdminGetField(props.permissions, "delete")}
      </Datagrid>
    </List>
  );
};
