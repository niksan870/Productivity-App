// in src/posts.js
import React from "react";
import {
  Create,
  Edit,
  SimpleForm,
  TextInput,
  TextField,
  EditButton,
  Show,
  SimpleShowLayout,
  TopToolbar,
  DeleteButton,
  UrlField,
} from "react-admin";

const required = (message = "Required") => (value) =>
  value ? undefined : message;

const redirect = (basePath, id, data) => `/#/Musics/`;

export const MusicCreate = (props) => {
  return (
    <Create {...props} redirect={redirect}>
      <SimpleForm>
        <TextInput
          validate={required()}
          label="Title of the Music
        "
          source="title"
        />
        <TextInput
          validate={required()}
          label="Paste a url for a background music"
          source="url"
        />
      </SimpleForm>
    </Create>
  );
};

export const MusicEdit = (props) => {
  return (
    <Edit {...props} redirect={redirect}>
      <SimpleForm>
        <TextInput
          validate={required()}
          label="Title of the Music
        "
          source="title"
        />
        <TextInput
          validate={required()}
          label="Paste a url for a background music"
          source="url"
        />
      </SimpleForm>
    </Edit>
  );
};

const MusicActions = ({ basePath, data }) => {
  return (
    <TopToolbar>
      <EditButton basePath={basePath} record={data} />
      <DeleteButton basePath={basePath} record={data} />
    </TopToolbar>
  );
};

export const MusicShow = (props) => {
  return (
    <Show {...props} actions={<MusicActions props={props} />} title="Music">
      <SimpleShowLayout>
        <TextField label="Title" source="title" />
        <UrlField label="Url of the Background Music" source="url" />
      </SimpleShowLayout>
    </Show>
  );
};
