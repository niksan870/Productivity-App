// in src/posts.js
import React from "react";
import {
  Create,
  Edit,
  SimpleForm,
  TextInput,
  DateInput,
  TextField,
  EditButton,
  SelectInput,
  Show,
  List,
  ShowButton,
  SimpleShowLayout,
  TabbedShowLayout,
  ReferenceField,
  ReferenceManyField,
  ImageField,
  SingleFieldList,
  ChipField,
  BooleanField,
  NumberField,
  DateField,
  Datagrid,
  Tab,
  TopToolbar,
  BooleanInput,
  DeleteButton,
} from "react-admin";
import { makeStyles, Chip } from "@material-ui/core";
import FormDialog from "../fields/FormDialog";
import { CustomFieldLinker } from "../fields/CustomImageField";
import Example from "../components/pomodoro/Example";
import { CopyToClickBoard } from "../fields/CopyToClickBoard";
import { useTranslate } from "react-admin";

const required = (message = "Required") => (value) =>
  value ? undefined : message;

export const GoalCreate = (props) => {
  return (
    <Create {...props}>
      <SimpleForm>
        <BooleanInput label="Private" source="private" />
        <TextInput validate={required()} source="title" />
        <TextInput validate={required()} source="description" />
        <SelectInput
          validate={required()}
          source="hours"
          choices={[
            { id: "1", name: "1" },
            { id: "2", name: "2" },
            { id: "3", name: "3" },
            { id: "4", name: "4" },
            { id: "5", name: "5" },
            { id: "6", name: "6" },
            { id: "7", name: "7" },
            { id: "8", name: "8" },
            { id: "9", name: "9" },
            { id: "10", name: "10" },
            { id: "11", name: "11" },
            { id: "12", name: "12" },
          ]}
        />
        <SelectInput
          validate={required()}
          source="minutes"
          choices={[
            { id: "00", name: "00" },
            { id: "05", name: "05" },
            { id: "10", name: "10" },
            { id: "15", name: "15" },
            { id: "20", name: "20" },
            { id: "25", name: "25" },
            { id: "30", name: "30" },
            { id: "35", name: "35" },
            { id: "40", name: "40" },
            { id: "45", name: "45" },
            { id: "50", name: "50" },
            { id: "55", name: "55" },
          ]}
        />
        <DateInput
          validate={required()}
          label="Dead Line Goal Setter"
          source="deadlineSetter"
        />
      </SimpleForm>
    </Create>
  );
};

export const GoalEdit = (props) => {
  return (
    <Edit {...props}>
      <SimpleForm>
        <BooleanInput label="Private" source="private" />
        <TextInput validate={required()} source="title" />
        <TextInput validate={required()} source="description" />
        <SelectInput
          source="hours"
          choices={[
            { id: "1", name: "1" },
            { id: "2", name: "2" },
            { id: "3", name: "3" },
            { id: "4", name: "4" },
            { id: "5", name: "5" },
            { id: "6", name: "6" },
            { id: "7", name: "7" },
            { id: "8", name: "8" },
            { id: "9", name: "9" },
            { id: "10", name: "10" },
            { id: "11", name: "11" },
            { id: "12", name: "12" },
          ]}
        />
        <SelectInput
          source="minutes"
          choices={[
            { id: "05", name: "05" },
            { id: "10", name: "10" },
            { id: "15", name: "15" },
            { id: "20", name: "20" },
            { id: "25", name: "25" },
            { id: "30", name: "30" },
            { id: "35", name: "35" },
            { id: "40", name: "40" },
            { id: "45", name: "45" },
            { id: "50", name: "50" },
            { id: "55", name: "55" },
          ]}
        />
        <DateInput label="Dead Line Goal Setter" source="deadlineSetter" />
      </SimpleForm>
    </Edit>
  );
};

const GoalActions = ({ basePath, data, resource, props }) => {
  let { editable } = data;
  let permissions = localStorage.getItem("permissions");
  return (
    <TopToolbar>
      {editable ? (
        <EditButton basePath={basePath} record={data} />
      ) : permissions.includes("ADMIN") ? (
        <EditButton basePath={basePath} record={data} />
      ) : null}
      {editable ? (
        <DeleteButton basePath={basePath} record={data} />
      ) : permissions.includes("ADMIN") ? (
        <DeleteButton basePath={basePath} record={data} />
      ) : null}
    </TopToolbar>
  );
};

export const GoalShow = (props) => {
  return (
    <Show
      {...props}
      actions={<GoalActions props={props} />}
      title="Goal Progress"
    >
      <TabbedShowLayout>
        <Tab label="details">
          <TextField label="Key" source="id" />
          <CopyToClickBoard />
          <TextField source="title" />
          <TextField label="Goal Status" source="private" />
          <TextField label="Description" source="description" />
          <TextField label="How Much Time Per Day" source="dailyTimePerDay" />
          <TextField label="Deadline Date" source="deadlineSetter" />
        </Tab>
        <Tab label="Participants' Graphs">
          <ReferenceManyField
            label="Participants"
            reference="goals"
            target="id"
            filter={{ method: "getGoalsWithProfilesAndGraphs" }}
          >
            <Datagrid expand={<PostPanel />}>
              <CustomFieldLinker method="user" />
              <TextField label="Time done today" source="goal.dailyTimePerDay" />
              <TextField label="Expected time to be done today" source="goal.dailyTimePerDay" />
            </Datagrid>
          </ReferenceManyField>
        </Tab>
        <Tab label="Manage Participants">
          <ReferenceManyField
            label="Participants"
            reference="profiles"
            target="id"
            filter={{ method: "getParticipants" }}
          >
            <Datagrid >
              <CustomFieldLinker method="profiles" />
              <DeleteButton undoable={false} label="ra.action.remove"/>
            </Datagrid>
          </ReferenceManyField>
        </Tab>
        <Tab label="Pomodoro">
          <ReferenceManyField
            label="Participants"
            reference="goals"
            target="id"
            filter={{ method: "getGoalsWithProfilesAndGraphs" }}
          >
            <Datagrid expand={<PostPanel />}>
              <CustomFieldLinker method="user" />
              <TextField source="goal.dailyTimePerDay" />
              <TextField source="goal.deadlineSetter" />
            </Datagrid>
          </ReferenceManyField>
        </Tab>
      </TabbedShowLayout>
    </Show>
  );
};

const PostPanel = ({ id, record, resource }) => (
  <Example {...JSON.parse(record.jsonData)} />
);
