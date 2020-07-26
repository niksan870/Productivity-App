// import Avatar from "react-avatar";
import React from "react";
import Avatar from "@material-ui/core/Avatar";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";

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

export const CustomImageField = ({ source, record }) => {
    const classes = useStyles();
    return (
        <div>
            {record.picture == null || record.picture == "" ? (
                <Avatar variant="square" className={classes.large}></Avatar>
            ) : (
                    <Avatar
                        variant="square"
                        className={classes.large}
                        src={record.picture}
                    />
                )}
        </div>
    );
};

export const CustomImageFieldCreatedBy = ({ source, record }) => {
    const classes = useStyles();
    return (
        <div>
            {record.createdBy != undefined ? <a href={"#/profiles/" + record.createdBy.id + "/show"}>
                {record.createdBy == null || record.createdBy == "" ? (
                    <Avatar variant="square" className={classes.large}></Avatar>
                ) : (
                        <Avatar
                            variant="square"
                            className={classes.large}
                            src={record.createdBy.picture}
                        />
                    )}
                <Typography variant="h5" gutterBottom>
                    {record.createdBy.username}
                </Typography>
            </a> : null}

        </div>
    );
};

export const CustomProfileLinker = ({ source, record }) => {
    const classes = useStyles();
    return (
        <div>
            {record != undefined ? <a href={"#/profiles/" + record.id + "/show"}>
                <Typography variant="h5" gutterBottom>
                    {record.name}
                </Typography>
            </a> : null}

        </div>
    );
};

export const CustomGoalLinker = ({ source, record }) => {
    const classes = useStyles();
    return (
        <div>
            {record != undefined ? <a href={"#/goals/" + record.id + "/show"}>
                <Typography variant="h5" gutterBottom>
                    {record.title}
                </Typography>
            </a> : null}

        </div>
    );
};