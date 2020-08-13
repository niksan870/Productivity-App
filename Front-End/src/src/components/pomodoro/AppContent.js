import React, { Component } from "react";
import CardHeader from "@material-ui/core/CardHeader";
import Select from "@material-ui/core/Select";
import App from "./App";
import Player from "./../player/Player";
import axios from "axios";
import { Query, Loading, Error, showNotification } from "react-admin";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Grid from "@material-ui/core/Grid";
import Container from "@material-ui/core/Container";
import MenuItem from "@material-ui/core/MenuItem";
import { makeStyles } from "@material-ui/core/styles";
import Switch from "@material-ui/core/Switch";
import FormGroup from "@material-ui/core/FormGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import { BASE_API_AUTH_URL } from "../../../constants";
import MyCalendar from "../calendar/MyCalendar";

export default class AppContent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      breakLength: 5,
      sessionLength: 25,
      additionalSettings: false,
      musicList: null,
      musicURL: "test",
      pomodorosResponse: null,
      token: localStorage.getItem("accessToken"),
    };

    this.handleSelect = this.handleSelect.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleMusicSelect = this.handleMusicSelect.bind(this);
  }

  handleChange(e) {
    this.setState({ ...this.state, [e.target.name]: e.target.checked });
  }

  handleMusicSelect(e) {
    axios({
      method: "get",
      url: BASE_API_AUTH_URL + "/music?page=0&pageSize=10",
      data: {},
      headers: {
        Authorization: "Bearer " + this.state.token,
      },
    }).then((response) => {
      this.setState({
        sessionLength: this.state.pomodorosResponse.sessionLength,
        breakLength: this.state.pomodorosResponse.breakLength,
        musicList: response.data.content,
        goalId: e.target.value,
        showGraph: true,
      });
      return Promise.resolve(response);
    });
    this.setState({ ...this.state, [e.target.name]: e.target.value });
  }

  handleSelect(e) {
    axios({
      method: "get",
      url: BASE_API_AUTH_URL + "/pomodoros/" + e.target.value,
      data: {},
      headers: {
        Authorization: "Bearer " + this.state.token,
      },
    })
      .then((response) => {
        let pomodorosResponse = response.data;
        this.setState({
          ...this.state,
          [pomodorosResponse]: pomodorosResponse,
        });
      })
      .catch((error) => {
        showNotification("Error: comment not approved", "warning");
      });
  }

  render() {
    return (
      <Query type="getOne" resource="goals" payload={{ id: "mine" }}>
        {({ data, loading, error }) => {
          if (loading) {
            return <Loading />;
          }
          if (error) {
            return <Error />;
          }

          const classes = makeStyles((theme) => ({
            root: {
              flexGrow: 1,
              backgroundColor: "red",
            },
            paper: {
              padding: theme.spacing(2),
              textAlign: "center",
              color: theme.palette.text.secondary,
            },
          }));

          if (data != null) {
            return (
              <div className={classes.root}>
                <CardHeader
                  title="Make your day more productive"
                  style={{ textAlign: "center" }}
                />
                <Grid container spacing={3}>
                  <Grid item xs={12}>
                    <FormGroup row style={{ justifyContent: "center" }}>
                      <FormControlLabel
                        style={{ justifyContent: "center" }}
                        control={
                          <Switch
                            checked={this.state.additionalSettings}
                            onChange={this.handleChange}
                            name="additionalSettings"
                            color="primary"
                          />
                        }
                        label="Customize pomodoro environment"
                      />
                    </FormGroup>
                  </Grid>
                  {this.state.additionalSettings != false ? (
                    <Grid container spacing={1}>
                      <Grid item xs={6}>
                        <Container style={{ textAlign: "center" }}>
                          <FormControl style={{ minWidth: 240 }}>
                            <InputLabel id="select-a-goal">
                              Choose a pomodoro setting
                            </InputLabel>
                            <Select
                              label="Choose a pomodoro setting"
                              name="pomodoro"
                              id="pomodoro"
                              onChange={this.handleSelect}
                              className="form-control"
                              id="select-a-goal"
                            >
                              {data.pomodoros != null
                                ? data.pomodoros.map((option, index) => (
                                    <MenuItem key={index} value={option.id}>
                                      {option.title}
                                    </MenuItem>
                                  ))
                                : null}
                            </Select>
                          </FormControl>
                        </Container>
                      </Grid>
                      <Grid item xs={6}>
                        <Container style={{ textAlign: "center" }}>
                          <FormControl style={{ minWidth: 240 }}>
                            <InputLabel id="select-a-goal">
                              Choose a music to work on
                            </InputLabel>
                            <Select
                              label="Choose a music to work on"
                              name="musicURL"
                              id="musicURL"
                              onChange={this.handleMusicSelect}
                              className="form-control"
                              id="select-a-goal"
                            >
                              {data.pomodoroMusic != null
                                ? data.pomodoroMusic.map((option, index) => (
                                    <MenuItem key={index} value={option.url}>
                                      {option.title}
                                    </MenuItem>
                                  ))
                                : null}
                            </Select>
                          </FormControl>
                        </Container>
                      </Grid>
                      <Grid item xs={12}>
                        {this.state.musicURL != null ? (
                          <Player url={this.state.musicURL} />
                        ) : null}
                      </Grid>
                    </Grid>
                  ) : null}

                  <Grid
                    item
                    xs={12}
                    id="dashboard"
                    style={{ textAlign: "center" }}
                  >
                    <App
                      defaultBreakLength={this.state.breakLength}
                      defaultSessionLength={this.state.sessionLength}
                      {...this.state}
                      {...data}
                    />
                  </Grid>
                </Grid>
              </div>
            );
          }
        }}
      </Query>
    );
  }
}
