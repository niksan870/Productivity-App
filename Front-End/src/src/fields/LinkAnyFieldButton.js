// react etc
import React, { Component, Children, cloneElement, Fragment } from 'react';
import PropTypes from 'prop-types';
import shouldUpdate from 'recompose/shouldUpdate';
import { withRouter } from 'react-router-dom';
import compose from 'recompose/compose';
// mui etc
import { withStyles } from '@material-ui/core/styles';
// ra etc
import { Link, linkToRecord } from 'react-admin';

const styles = {
    link: {
        cursor: 'pointer',
        '& *': {
            cursor: 'pointer',
        },
    },
};

const sanitizeRestClasses = ({ link, ...rest }) => rest;
const sanitizeRestProps = ({
    classes,
    to,
    relative,
    disabled,
    history,
    location,
    match,
    staticContext,
    ...rest
}) => rest;

class LinkAnyFieldButton extends Component {
    static propTypes = {
        // passed by parent
        basePath: PropTypes.string,
        children: PropTypes.any,
        record: PropTypes.object,
        classes: PropTypes.object,
        // own props
        to: PropTypes.string,
        relative: PropTypes.bool,
        disabled: PropTypes.oneOfType([PropTypes.func, PropTypes.bool]),
        // withRouter props
        match: PropTypes.object,
    };
    static defaultProps = {
        to: 'show',
    };

    render() {
        const {
            basePath = '',
            children,
            record = {},
            classes,
            to,
            relative,
            disabled,
            match,
        } = this.props;

        const recordLink = to.startsWith('/')
            ? to.replace(/\/:([\w-]+)/g, (m, param) => {
                return `/${record[param]}`;
            })
            : linkToRecord(basePath, record.id, to);
        const completeTo = relative ? `${match.url}${recordLink}` : recordLink;
        const rest = sanitizeRestProps(this.props);
        const restClasses = sanitizeRestClasses(classes);
        const isDisabled =
            typeof disabled === 'function' ? disabled(record) : disabled;

        const countChildren = Children.count(children);

        const childElements =
            countChildren === 1
                ? cloneElement(children, {
                    record,
                    basePath,
                    classes: restClasses,
                })
                : Children.map(children, field =>
                    cloneElement(field, {
                        record,
                        basePath,
                        classes: restClasses,
                        ...rest,
                    })
                );

        return isDisabled ? (
            <Fragment>{childElements}</Fragment>
        ) : (
                <Link to={`${completeTo}`} className={classes.link}>
                    {childElements}
                </Link>
            );
    }
}

const enhance = compose(
    withRouter, // adds props: history, location, match, staticContext
    withStyles(styles),
    shouldUpdate(
        (props, nextProps) =>
            props.translate !== nextProps.translate ||
            (props.record &&
                nextProps.record &&
                props.record.id !== nextProps.record.id) ||
            props.basePath !== nextProps.basePath ||
            (props.record == null && nextProps.record != null)
    )
);

export default enhance(LinkAnyFieldButton);