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
  Line,
  LineChart,
} from "recharts";

const Example = (props) => {
  return (
    <LineChart
      width={500}
      height={300}
      data={props.dataGraph}
      margin={{
        top: 5, right: 30, left: 20, bottom: 5,
      }}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="name" />
      <YAxis />
      <Tooltip />
      <Legend />
      <Line type="monotone" dataKey="expectedTime" stroke="#8884d8" activeDot={{ r: 8 }} />
      <Line type="monotone" dataKey="timeDone" stroke="#82ca9d" />
    </LineChart>
  );
};

export default Example;
