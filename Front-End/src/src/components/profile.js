// in src/posts.js
import React from "react";
import {
  Edit,
  SimpleForm,
  TextInput,
  DateInput,
  TextField,
  EditButton,
  SelectInput,
  Show,
  ImageField,
  ImageInput,
  TabbedShowLayout,
  Tab,
  ReferenceManyField,
  Datagrid,
  ShowButton,
  DeleteButton,
  ReferenceField,
  TopToolbar
} from "react-admin";
import FormDialog from "../fields/FormDialog";

import Avatar from "@material-ui/core/Avatar";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import { CustomImageField } from "../fields/CustomImageField";

import ChatBubbleIcon from "@material-ui/icons/ChatBubble";
import { withStyles } from "@material-ui/core/styles";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
import axios from "axios";

import LinkAnyFieldButton from "../fields/LinkAnyFieldButton";
import { useQueryWithStore, Loading, Error } from "react-admin";

function formatPicture(value) {
  if (!value || typeof value === "string") {
    return { picture: value };
  } else {
    return value;
  }
}

const ProfileActions = ({ basePath, data, resource, props }) => {
  let currentUserId = data.editProfile;
  let permissions = localStorage.getItem("permissions");
  return (
    <TopToolbar>
      {currentUserId ? (
        <EditButton basePath={basePath} record={data} />
      ) : permissions.includes("ADMIN") ? (
        <EditButton basePath={basePath} record={data} />
      ) : null}
      {currentUserId ? (
        <DeleteButton basePath={basePath} record={data} />
      ) : permissions.includes("ADMIN") ? (
        <DeleteButton basePath={basePath} record={data} />
      ) : null}
    </TopToolbar>
  );
};

export const ProfileEdit = (props) => {
  return (
    <Edit {...props} actions={<ProfileActions />}>
      <SimpleForm redirect="show" submitOnEnter={true}>
        {props.permissions.includes("ADMIN") ? (
          <TextInput disabled label="Id" source="id" />
        ) : null}
        <ImageInput format={formatPicture} source="picture" accept="image/*">
          <ImageField source="picture" />
        </ImageInput>
        <TextInput source="name" />
        {/* <TextInput source="id" /> */}
        <TextInput source="phoneNumber" />
        <SelectInput
          source="gender"
          choices={[
            { id: "FEMALE", name: "FEMALE" },
            { id: "MALE", name: "MALE" },
            { id: "OTHER", name: "OTHER" },
          ]}
        />
        <DateInput label="Date Of Birth" source="dateOfBirth" />
        <TextInput source="city" />
        <TextInput source="country" />
      </SimpleForm>
    </Edit>
  );
};

export const ProfileShow = (props) => {
  return (
    <Show {...props} title="Profile view" actions={<ProfileActions />}>
      <TabbedShowLayout>
        <Tab label="PRrfile">
          <CustomImageField source="picture" title="Picture" />
          <TextField label="name" source="name" />
          <TextField label="Phome Number" source="phoneNumber" />
          <TextField label="Date Of Birth" source="dateOfBirth" />
          <TextField label="City" source="city" />
          <TextField label="Country" source="country" />
        </Tab>
        <Tab label="Goals">
          <ReferenceManyField
            label="Goals"
            reference="goals"
            target="id"
            filter={{ method: "getGoalsFromProfile" }}
          >
            <Datagrid>
              <TextField source="title" />
              <TextField source="description" />
              <ShowButton />
            </Datagrid>
          </ReferenceManyField>
        </Tab>
      </TabbedShowLayout>
    </Show>
  );
};
