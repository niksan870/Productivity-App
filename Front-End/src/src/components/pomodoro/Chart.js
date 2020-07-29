import React, { Component } from "react";
import "./Chart.css";
import "../../../../node_modules/react-vis/dist/style.css";
import {
  XYPlot,
  XAxis,
  YAxis,
  HorizontalGridLines,
  VerticalGridLines,
  LineSeries,
  makeWidthFlexible,
} from "react-vis";
import { DayView } from "@devexpress/dx-react-scheduler";

const FlexibleXYPlot = makeWidthFlexible(XYPlot);

class Chart extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      crosshairValues: [],
    };
  }

  render() {
    let timeDoneForEachDayUntilTheDeadLine,
      expectedTimeToBeDone,
      secondsOfExpectedTimePerDay;

    if (this.props.gaolData != null) {
      timeDoneForEachDayUntilTheDeadLine = JSON.parse(
        this.props.gaolData.stringifiedJsonData
      ).dataGraph;
      secondsOfExpectedTimePerDay = this.props.dailyTimeToBeReached;
    }

    if (timeDoneForEachDayUntilTheDeadLine != undefined) {
      timeDoneForEachDayUntilTheDeadLine = timeDoneForEachDayUntilTheDeadLine.map(
        function (item) {
          item.x = new Date(item.x);
          return item;
        }
      );

      var newObject = JSON.parse(
        JSON.stringify(timeDoneForEachDayUntilTheDeadLine)
      );

      expectedTimeToBeDone = newObject.map(function (item) {
        item.x = new Date(item.x);
        item.y = secondsOfExpectedTimePerDay;
        return item;
      });
    }

    console.log(123);
    return (
      <FlexibleXYPlot className="Chart" xType="time" height={300}>
        <HorizontalGridLines />
        <VerticalGridLines />
        <XAxis title="Time Until Deadline" />
        <YAxis
          title="Time To Be Done"
          tickFormat={function tickFormat(d) {
            let date = new Date(null);
            date.setSeconds(d);
            return date.toISOString().substr(11, 5);
          }}
        />
        {timeDoneForEachDayUntilTheDeadLine != undefined ? (
          <LineSeries data={timeDoneForEachDayUntilTheDeadLine} />
        ) : null}
        {expectedTimeToBeDone != undefined ? (
          <LineSeries data={expectedTimeToBeDone} />
        ) : null}
      </FlexibleXYPlot>
      // <div>asdasd</div>
    );
  }
}

export default Chart;
