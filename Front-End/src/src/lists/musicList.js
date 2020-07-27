
import React from "react";
import {
  List,
  TextField,
  EditButton,
  DeleteButton,
  ShowButton,
  Datagrid,
  UrlField,
} from "react-admin";
import { cloneElement, useMemo } from "react";

import {
  TopToolbar,
  CreateButton,
  ExportButton,
  Button,
  sanitizeListRestProps,
} from "react-admin";

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

export const MusicList = (props) => {
  return (
    <List {...props}>
      <Datagrid>
        <TextField source="title" />
        <UrlField source="url" />
        <ShowButton />
        <EditButton />
        <DeleteButton />
      </Datagrid>
    </List>
  );
};