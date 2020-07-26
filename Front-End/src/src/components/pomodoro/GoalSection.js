import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";

export default function GoalSection({ data }) {
  if (data != null) {
    return (
      <Card>
        <CardActionArea>
          <CardContent>
            <Typography gutterBottom variant="h5" component="h2">
              {data.title}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
              {data.description}
            </Typography>
            <Typography variant="body3" color="textThird" component="p">
              Expected time to be done today = {data.hours + ":" + data.minutes}{" "}
              hours
            </Typography>
          </CardContent>
        </CardActionArea>
      </Card>
    );
  } else {
    return <Card />;
  }
}
