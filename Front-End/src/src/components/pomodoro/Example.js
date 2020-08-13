import React from "react";
import PropTypes from "prop-types";
import moment from "moment";

import {
  CartesianGrid,
  Legend,
  ResponsiveContainer,
  Scatter,
  ScatterChart,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

const Example = (props) => {
  return (
    <ResponsiveContainer width="95%" height={500}>
      <ScatterChart>
        <XAxis
          dataKey="time"
          name="Time"
          type="number"
          tickFormatter={formatXAxis}
        />
        <YAxis dataKey="value" name="Value" />
        <Tooltip />
        <Scatter
          data={props.dataGraph}
          line={{ stroke: "#eee" }}
          lineJointType="monotoneX"
          lineType="joint"
          name="Values"
        />
      </ScatterChart>
    </ResponsiveContainer>
  );
};

function formatXAxis(tickItem) {
  // If using moment.js
  return moment(tickItem).format("HH:mm:ss");
}

export default Example;
