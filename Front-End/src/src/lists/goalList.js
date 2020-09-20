import React from "react";
import {
  List,
  TextField,
  EditButton,
  DeleteButton,
  ShowButton,
  Datagrid,
} from "react-admin";
import { cloneElement } from "react";
import FormDialog from "../fields/FormDialog";
import { makeStyles, Chip } from "@material-ui/core";
import { useTranslate } from "react-admin";
import { CustomFieldLinker } from "../fields/CustomImageField";

import {
  TopToolbar,
  CreateButton,
  sanitizeListRestProps,
  Filter,
  SearchInput,
  ReferenceManyField,
} from "react-admin";

import Example from "../components/pomodoro/Example";
const ListActions = ({
  currentSort,
  className,
  resource,
  filters,
  displayedFilters,
  exporter, // you can hide ExportButton if exporter = (null || false)
  filterValues,
  permanentFilter,
  hasCreate, // you can hide CreateButton if hasCreate = false
  basePath,
  selectedIds,
  onUnselectItems,
  showFilter,
  maxResults,
  total,
  ...rest
}) => (
    <TopToolbar className={className} {...sanitizeListRestProps(rest)}>
      {filters &&
        cloneElement(filters, {
          resource,
          showFilter,
          displayedFilters,
          filterValues,
          context: "button",
        })}
      <CreateButton basePath={basePath} />
      <FormDialog />
    </TopToolbar>
  );

ListActions.defaultProps = {
  selectedIds: [],
  onUnselectItems: () => null,
};

const useQuickFilterStyles = makeStyles((theme) => ({
  chip: {
    marginBottom: theme.spacing(1),
  },
}));

const QuickFilter = ({ label }) => {
  const translate = useTranslate();
  const classes = useQuickFilterStyles();
  return <Chip className={classes.chip} label={translate(label)} />;
};

const PostFilter = (props) => {
  return (
    <Filter {...props}>
      <SearchInput source="q" alwaysOn />
      <QuickFilter source="all" label="ra.action.all" defaultValue={true} />
    </Filter>
  );
};

// function ifAdminGetField(permissions, string, props = null) {
//   if (permissions !== undefined) {
//     if (permissions.includes("ROLE_ADMIN")) {
//       if (string === "delete") {
//         return <DeleteButton />;
//       } else if (string === "edit") {
//         return <EditButton />;
//       } else {
//         return <TextField source={string} />;
//       }
//     }
//   }
// }

export const GoalsList = (props) => {
  return (
    <List {...props} filters={<PostFilter />} actions={<ListActions />}>
      <Datagrid>
        <TextField source="title" />
        <TextField source="description" />
        <ReferenceManyField
          label="Owners"
          reference="profiles"
          target="id"
          filter={{ method: "getGoalOwner" }}
        >
          <Datagrid >
            <TextField source="name" />
            <ShowButton />
          </Datagrid>
        </ReferenceManyField>
        <TextField source="dailyTimePerDay" />
        <ShowButton />
      </Datagrid>
    </List>
  );
};

const PostPanel = ({ id, record, resource }) => (
  <Example {...JSON.parse(record.jsonData)} />
);
