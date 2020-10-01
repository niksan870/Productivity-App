import React from "react";
import Avatar from "@material-ui/core/Avatar";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";
import { Query, Loading } from "react-admin";

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
  if (source == "profile") {
    console.log(source);
    return (
      <Button
        component={Link}
        to={{ pathname: "/profiles/" + record.createdBy.id }}
      >
        {record.createdBy.name}
      </Button>
    );
  } else {
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
  }
};

CustomImageField.defaultProps = { label: "Picture" };

export const CustomFieldLinker = ({ source, record, method }) => {
  const classes = useStyles();
  // console.log(record)
  if (method == "user") {
    return (
      <Button
        component={Link}
        to={{ pathname: `/profiles/${record.user.id}/show` }}
      >
        {record.user.createdAt == null || record.user.createdAt == "" ? (
          <Avatar variant="square" className={classes.large}></Avatar>
        ) : (
          <Avatar
            variant="square"
            className={classes.large}
            src={record.user.picture}
          />
        )}
        <Typography variant="h5" gutterBottom>
          {record.user.name}
        </Typography>
      </Button>
    );
  } else if (method == "profiles") {
    // console.log(record)
    return (
      <Button
        component={Link}
        to={{ pathname: `/profiles/${record.createdBy.id}/show` }}
      >
        {record.createdBy.picture == null || record.createdBy.picture == "" ? (
          <Avatar variant="square" className={classes.large}></Avatar>
        ) : (
          <Avatar
            variant="square"
            className={classes.large}
            src={record.createdBy.picture}
          />
        )}
        <Typography variant="h5" gutterBottom>
          {record.createdBy.name}
        </Typography>
      </Button>
    );
  } else if ("participant") {
    return record != undefined ? (
      <Button component={Link} to={{ pathname: `/profiles/${record.id}/show` }}>
        {record.picture == null || record.picture == "" ? (
          <Avatar variant="square" className={classes.large}></Avatar>
        ) : (
          <Avatar
            variant="square"
            className={classes.large}
            src={record.picture}
          />
        )}
        <Typography variant="h5" gutterBottom>
          {record.name}
        </Typography>
      </Button>
    ) : null;
  }
};

CustomFieldLinker.defaultProps = { label: "User Profile" };
