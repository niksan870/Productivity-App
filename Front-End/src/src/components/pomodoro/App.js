import React, { Component } from "react";
import Settings from "./Settings";
import Times from "./Times";
import Controller from "./Controller";
import axios from "axios";
import "./App.css";
import { Query, Loading, Error, showNotification } from "react-admin";
import Chart from "./Chart";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import GoalSection from "./GoalSection";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Grid from "@material-ui/core/Grid";
import Container from "@material-ui/core/Container";
import MenuItem from "@material-ui/core/MenuItem";
import Select from "@material-ui/core/Select";
import { BASE_API_AUTH_URL, BASE_API_URL } from "../../../constants";
import Example from "./Example";

class App extends Component {
  constructor(props) {
    super(props);

    this.audioBeep = React.createRef();

    this.state = {
      breakLength: Number.parseInt(this.props.defaultBreakLength, 10),
      sessionLength: Number.parseInt(this.props.defaultSessionLength, 10),
      timeLabel: "Session",
      timeLeftInSecond:
        Number.parseInt(this.props.defaultSessionLength, 10) * 60,
      timeDoneSoFar: null,
      isStart: false,
      goalId: null,
      showGraph: false,
      goalData: null,
      timerInterval: null,
      dailyTimeToBeReached: null,
      counter: -1,
      selectedGoal: null,
      token: localStorage.getItem("accessToken"),
    };

    this.onIncreaseBreak = this.onIncreaseBreak.bind(this);
    this.onDecreaseBreak = this.onDecreaseBreak.bind(this);
    this.onIncreaseSession = this.onIncreaseSession.bind(this);
    this.onDecreaseSession = this.onDecreaseSession.bind(this);
    this.onReset = this.onReset.bind(this);
    this.onStartStop = this.onStartStop.bind(this);
    this.decreaseTimer = this.decreaseTimer.bind(this);
    this.phaseControl = this.phaseControl.bind(this);
    this.submitTime = this.submitTime.bind(this);
    this.handleSelect = this.handleSelect.bind(this);
  }

  onIncreaseBreak() {
    if (this.state.breakLength < 60 && !this.state.isStart) {
      this.setState({
        breakLength: this.state.breakLength + 1,
      });
    }
  }

  onDecreaseBreak() {
    if (this.state.breakLength > 1 && !this.state.isStart) {
      this.setState({
        breakLength: this.state.breakLength - 1,
      });
    }
  }

  onIncreaseSession() {
    if (this.state.sessionLength < 60 && !this.state.isStart) {
      this.setState({
        sessionLength: this.state.sessionLength + 1,
        timeLeftInSecond: (this.state.sessionLength + 1) * 60,
      });
    }
  }

  onDecreaseSession() {
    if (this.state.sessionLength > 1 && !this.state.isStart) {
      this.setState({
        sessionLength: this.state.sessionLength - 1,
        timeLeftInSecond: (this.state.sessionLength - 1) * 60,
      });
    }
  }

  onReset() {
    this.setState({
      breakLength: Number.parseInt(this.props.defaultBreakLength, 10),
      sessionLength: Number.parseInt(this.props.defaultSessionLength, 10),
      timeLabel: "Session",
      timeLeftInSecond:
        Number.parseInt(this.props.defaultSessionLength, 10) * 60,
      isStart: false,
      timerInterval: null,
    });

    this.audioBeep.current.pause();
    this.audioBeep.current.currentTime = 0;
    this.state.timerInterval && clearInterval(this.state.timerInterval);
  }

  submitTime() {
    let goalId = this.state.goalId;

    console.log(goalId)
    let timeDoneSoFar =
      this.state.sessionLength * 60 - this.state.timeLeftInSecond;

    let actualTimeDoneSoFar = timeDoneSoFar - this.state.timeDoneSoFar;
    axios({
      method: "put",
      url: BASE_API_URL + "/goals/logTime/" + goalId,
      data: { time: actualTimeDoneSoFar },
      headers: {
        Authorization: "Bearer " + this.state.token,
      },
    })
      .then((response) => {
        let hours = parseInt(response.data.hours) * 3600;
        let minutes = (parseInt(response.data.minutes) % 3600) * 60;
        let time = hours + minutes;
        this.setState({
          goalData: JSON.parse(response.data.stringifiedJsonData),
          dailyTimeToBeReached: time,
          counter: 0,
          selectedGoal: response.data,
        });
      })
      .catch((error) => {
        this.props.showNotification("Error: something went wrong", "warning");
      });
  }

  onStartStop() {
    if (!this.state.isStart) {
      if (this.state.selectedGoal != null) {
        this.setState({
          isStart: !this.state.isStart,
          timerInterval: setInterval(() => {
            this.decreaseTimer();
            this.phaseControl();
            if (this.state.timeLabel == "Session") {
              this.submitTime();
            }
          }, 100),
        });
      } else {
        this.props.showNotification("Choose a goal to work on", "warning");
      }
    } else {
      this.audioBeep.current.pause();
      this.audioBeep.current.currentTime = 0;
      this.state.timerInterval && clearInterval(this.state.timerInterval);

      this.setState({
        isStart: !this.state.isStart,
        timerInterval: null,
      });
    }
  }

  decreaseTimer() {
    this.setState({
      timeLeftInSecond: this.state.timeLeftInSecond - 1,
      counter: this.state.counter + 1,
      timeDoneSoFar:
        this.state.sessionLength * 60 - this.state.timeLeftInSecond,
    });
  }

  phaseControl() {
    if (this.state.timeLeftInSecond === 0) {
      this.audioBeep.current.play();
    } else if (this.state.timeLeftInSecond === -1) {
      if (this.state.timeLabel === "Session") {
        this.setState({
          timeLabel: "Break",
          timeLeftInSecond: this.state.breakLength * 60,
        });
      } else {
        this.setState({
          timeLabel: "Session",
          timeLeftInSecond: this.state.sessionLength * 60,
        });
      }
    }
  }

  handleSelect(e) {
    // axios({
    //   method: "get",
    //   url: BASE_API_AUTH_URL + "/goals/" + e.target.value,
    //   data: {},
    //   headers: {
    //     Authorization: "Bearer " + this.state.token,
    //   },
    // })
    //   .then((response) => {
    let hours = parseInt(e.target.value.hours) * 3600;
    let minutes = (parseInt(e.target.value.minutes) % 3600) * 60;
    let time = hours + minutes;

    this.setState({
      goalData: JSON.parse(e.target.value.stringifiedJsonData),
      selectedGoal: e.target.value,
      dailyTimeToBeReached: time,
      breakLength: Number.parseInt(this.props.defaultBreakLength, 10),
      sessionLength: Number.parseInt(this.props.defaultSessionLength, 10),
      timeLabel: "Session",
      timeLeftInSecond:
        Number.parseInt(this.props.defaultSessionLength, 10) * 60,
      isStart: false,
      timerInterval: null,
      goalId: e.target.value.id,
      showGraph: true,
    });
    //   })
    //   .catch((error) => {
    //     this.props.showNotification("Error: comment not approved", "warning");
    //   });


  }

  componentWillReceiveProps(props) {
    this.setState({
      breakLength: Number.parseInt(props.defaultBreakLength, 10),
      sessionLength: Number.parseInt(props.defaultSessionLength, 10),
      timeLeftInSecond: Number.parseInt(props.defaultSessionLength, 10) * 60,
    });
  }

  render() {
    console.log(this.state.showGraph);
    return (
      <div>
        <div className="pomodoro-clock">
          <Settings
            breakLength={this.state.breakLength}
            sessionLength={this.state.sessionLength}
            isStart={this.state.isStart}
            onDecreaseBreak={this.onDecreaseBreak}
            onDecreaseSession={this.onDecreaseSession}
            onIncreaseBreak={this.onIncreaseBreak}
            onIncreaseSession={this.onIncreaseSession}
          />

          <Times
            timeLabel={this.state.timeLabel}
            timeLeftInSecond={this.state.timeLeftInSecond}
          />

          <Controller
            onReset={this.onReset}
            onStartStop={this.onStartStop}
            isStart={this.state.isStart}
          />
          <audio
            id="beep"
            preload="auto"
            src="http://www.soundjay.com/button/beep-07.wav"
            ref={this.audioBeep}
          ></audio>
        </div>
        <Grid
          style={{ backgroundColor: "white" }}
          container
          spacing={0}
          direction="column"
          alignItems="center"
          justify="center"
        >
          <Container>
            <FormControl style={{ minWidth: 220 }}>
              <InputLabel id="select-a-goal">
                Choose a goal to work on
                    </InputLabel>
              <Select
                label="Choose a goal to work on"
                name="goal"
                id="goal"
                onChange={this.handleSelect}
                className="form-control"
                id="select-a-goal"
              >
                {this.props.goals.map((option) => (
                  <MenuItem value={option}>{option.title}</MenuItem>
                ))}
              </Select>
            </FormControl>
            {/* <GoalSection
              data={
                this.state.selectedGoal != null
                  ? this.state.selectedGoal
                  : null
              }
            /> */}
            {this.state.showGraph ? (
              <Example />
            ) : null}
          </Container>
        </Grid>
      </div>
    );
  }
}

App.propTypes = {
  record: PropTypes.object,
  showNotification: PropTypes.func,
};

export default connect(null, {
  showNotification,
})(App);
