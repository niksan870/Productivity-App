import React from "react";
import {
  Create,
  Edit,
  SimpleForm,
  TextInput,
  DateInput,
  ReferenceManyField,
  Datagrid,
  TextField,
  DateField,
  EditButton,
  SelectInput,
  TopToolbar,
  DeleteButton,
  SimpleShowLayout,
} from "react-admin";

const UserActions = ({ basePath, data, resource, props }) => {
  let currentUserId = localStorage.getItem("userProfileID");
  let permissions = localStorage.getItem("permissions");
  let userId = props.id;
  return (
    <TopToolbar>
      {currentUserId == userId ? (
        <EditButton basePath={basePath} record={data} />
      ) : permissions.includes("ADMIN") ? (
        <EditButton basePath={basePath} record={data} />
      ) : null}
      {currentUserId == userId ? (
        <DeleteButton basePath={basePath} record={data} />
      ) : permissions.includes("ADMIN") ? (
        <DeleteButton basePath={basePath} record={data} />
      ) : null}
    </TopToolbar>
  );
};

export const UserEdit = (props) => {
  return (
    <Edit {...props}>
      <SimpleForm>
        <TextInput disabled label="Id" source="id" />
        <TextInput disabled source="username" />
        <SelectInput
          source="roles"
          choices={[
            { id: "ADMIN", name: "ADMIN" },
            { id: "MANAGER", name: "MANAGER" },
            { id: "USER", name: "USER" },
          ]}
        />
        <TextInput disabled source="email" />
        <TextInput source="active" />
      </SimpleForm>
    </Edit>
  );
};

export const UserShow = (props) => {
  return (
    <Edit {...props} actions={<UserActions props={props} />}>
      <SimpleShowLayout>
        <TextField label="Id" source="id" />
        <TextField source="username" />
        <TextField source="roles" />
        <TextField disabled source="email" />
        <TextField source="active" />
      </SimpleShowLayout>
    </Edit>
  );
};
