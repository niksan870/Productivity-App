import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import LaunchIcon from "@material-ui/icons/Launch";

const useStyles = makeStyles({
  link: {
    textDecoration: "none",
  },
  icon: {
    width: "0.5em",
    paddingLeft: 2,
  },
});

const MyUrlField = ({ record = {}, source, href }) => {
  const classes = useStyles();
  let link = href + record[source].id;
  return (
    <a href={link} className={classes.link}>
      User Profile
      <LaunchIcon className={classes.icon} />
    </a>
  );
};

export default MyUrlField;
