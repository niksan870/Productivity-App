import React from "react";
import {
  List,
  TextField,
  EditButton,
  DeleteButton,
  ShowButton,
  Datagrid,
} from "react-admin";
import { CustomImageField } from "../fields/CustomImageField";

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

export const ProfilesList = (props) => {
  return (
    <List {...props}>
      <Datagrid>
        {ifAdminGetField(props.permissions, "id")}
        <CustomImageField />
        <TextField source="name" />
        <TextField source="phoneNumber" />
        <TextField source="gender" />
        <TextField source="dateOfBirth" />
        <TextField source="city" />
        <TextField source="country" />
        <TextField source="goals" />
        {ifAdminGetField(props.permissions, "createdAt")}
        {ifAdminGetField(props.permissions, "updatedAt")}
        <ShowButton />
        {ifAdminGetField(props.permissions, "edit", props)}
        {ifAdminGetField(props.permissions, "delete", props)}
      </Datagrid>
    </List>
  );
};