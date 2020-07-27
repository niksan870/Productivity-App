import React, { Component } from "react";
import ReactPlayer from "react-player";

export default class Player extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        {this.props.url != null ? (
          <ReactPlayer url={this.props.url} volume={0.1} height="200" playing />
        ) : null}
      </div>
    );
  }
}
