// import Avatar from "react-avatar";
import React from "react";
import Avatar from "@material-ui/core/Avatar";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";
import {CopyToClipboard} from 'react-copy-to-clipboard';

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



export const CopyToClickBoard = ({ source, record, method }) => {
    const classes = useStyles();
    return <CopyToClipboard text={record.id}>
    <Button variant="contained">Copy To Click Board</Button>
  </CopyToClipboard>
  
};

CopyToClickBoard.defaultProps = { label: "Copy To Click Board" };
