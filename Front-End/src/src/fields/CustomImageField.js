// import Avatar from "react-avatar";
import React from "react";
import Avatar from "@material-ui/core/Avatar";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
    "& > *": {
      margin: theme.spacing(1),
    },
  },
  small: {
    width: theme.spacing(3),
    height: theme.spacing(3),
  },
  large: {
    width: theme.spacing(11),
    height: theme.spacing(11),
  },
}));

export const CustomImageField = ({ source, record }) => {
  const classes = useStyles();
  return (
    <Button>
      {record.picture == null || record.picture == "" ? (
        <Avatar variant="square" className={classes.large}></Avatar>
      ) : (
        <Avatar
          variant="square"
          className={classes.large}
          src={record.picture}
        />
      )}
    </Button>
  );
};

CustomImageField.defaultProps = { label: "Picture" };

export const CustomFieldLinker = ({ source, record, method }) => {
  const classes = useStyles();
  return record.createdBy != undefined ? (
    <Button
      component={Link}
      to={{ pathname: `/${method}/${record.createdBy.id}/show` }}
    >
      {record.createdBy == null || record.createdBy == "" ? (
        <Avatar variant="square" className={classes.large}></Avatar>
      ) : (
        <Avatar
          variant="square"
          className={classes.large}
          src={record.createdBy.picture}
        />
      )}
      <Typography variant="h5" gutterBottom>
        {record.createdBy.username}
      </Typography>
    </Button>
  ) : null;
};
CustomFieldLinker.defaultProps = { label: "User Profile" };
