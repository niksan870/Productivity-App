import React from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import LockOpenIcon from "@material-ui/icons/LockOpen";
import FormControl from "@material-ui/core/FormControl";
import { BASE_API_URL } from "../../constants";
import { useDataProvider, useNotify, useRedirect } from "react-admin";
import axios from "axios";

export default function FormDialog() {
  const redirect = useRedirect();
  const [open, setOpen] = React.useState(false);
  const [id, setId] = React.useState(null);
  const [token, setToken] = React.useState(localStorage.getItem("accessToken"));

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = (e) => {
    // console.log(e.target);
    setOpen(false);
  };

  const handleSubmit = (event) => {
    axios({
      method: "post",
      url: BASE_API_URL + "/goals/sendRequest/" + id,
      headers: {
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => {
        // console.log(response);
        setOpen(false);
        redirect("/goals");
      })
      .catch((error) => {
        // this.props.showNotification("Error: something went wrong", "warning");
      });
  };

  return (
    <div>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        <LockOpenIcon />
        Join someone's Goal
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">Add a goal</DialogTitle>
        <DialogContent style={{ minWidth: 380 }}>
          <DialogContentText>
            Paste the ID of the private Goal
          </DialogContentText>
          <TextField
            onChange={(e) => setId(e.target.value)}
            autoFocus
            margin="dense"
            id="goalsId"
            label="Goal's ID"
            type="text"
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary">
            Send
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
