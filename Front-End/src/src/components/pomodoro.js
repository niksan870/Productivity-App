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
  BooleanInput,
  NumberInput,
} from "react-admin";

const required = (message = "Required") => (value) =>
  value ? undefined : message;

const redirect = (basePath, id, data) => `/#/pomodoros/`;

export const PomodoroCreate = (props) => {
  return (
    <Create {...props} redirect={redirect}>
      <SimpleForm>
        <BooleanInput label="Set As Default" source="current" />
        <TextInput
          validate={required()}
          label="Title of the Pomodoro
        "
          source="title"
        />
        <NumberInput
          validate={required()}
          max={60}
          label="Set Session Length"
          source="sessionLength"
        />
        <NumberInput
          max={12}
          validate={required()}
          label="Set Break Length"
          source="breakLength"
        />
      </SimpleForm>
    </Create>
  );
};

export const PomodoroEdit = (props) => {
  console.log(props);
  return (
    <Edit {...props} redirect={redirect}>
      <SimpleForm>
        <BooleanInput label="Set As Default" source="current" />
        <TextInput
          validate={required()}
          label="Title of the Pomodoro
        "
          source="title"
        />
        <NumberInput
          validate={required()}
          max={60}
          label="Set Session Length"
          source="sessionLength"
        />
        <NumberInput
          max={12}
          validate={required()}
          label="Set Break Length"
          source="breakLength"
        />
      </SimpleForm>
    </Edit>
  );
};

const PomodoroActions = ({ basePath, data }) => {
  return (
    <TopToolbar>
      <EditButton basePath={basePath} record={data} />
      <DeleteButton basePath={basePath} record={data} />
    </TopToolbar>
  );
};

export const PomodoroShow = (props) => {
  return (
    <Show
      {...props}
      actions={<PomodoroActions props={props} />}
      title="Pomodoro"
    >
      <SimpleShowLayout>
        <TextField label="Title" source="title" />
        <TextField label="Session Length" source="sessionLength" />
        <TextField label="Break Length" source="breakLength" />
        <TextField label="Default Pomodoro" source="current" />
      </SimpleShowLayout>
    </Show>
  );
};
