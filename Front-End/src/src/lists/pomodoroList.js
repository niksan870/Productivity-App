import React from "react";
import {
  List,
  TextField,
  EditButton,
  DeleteButton,
  ShowButton,
  Datagrid,
} from "react-admin";
import { cloneElement, useMemo } from "react";

import {
  TopToolbar,
  CreateButton,
  ExportButton,
  sanitizeListRestProps,
} from "react-admin";

const ListActions = ({
  currentSort,
  className,
  resource,
  filters,
  displayedFilters,
  exporter,
  filterValues,
  permanentFilter,
  hasCreate,
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
      <ExportButton
        disabled={total === 0}
        resource={resource}
        sort={currentSort}
        filter={{ ...filterValues, ...permanentFilter }}
        exporter={exporter}
        maxResults={maxResults}
      />
    </TopToolbar>
  );

ListActions.defaultProps = {
  selectedIds: [],
  onUnselectItems: () => null,
};

export const PomodoroList = (props) => {
  return (
    <List {...props}>
      <Datagrid>
        <TextField source="title" />
        <TextField source="sessionLength" />
        <TextField source="breakLength" />
        <TextField source="current" />
        <ShowButton />
        <EditButton />;
        <DeleteButton />
      </Datagrid>
    </List>
  );
};