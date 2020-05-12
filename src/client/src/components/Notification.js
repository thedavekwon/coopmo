import React from "react";
import {connect} from "react-redux";
import {deleteNotification} from "../redux/actions";
import Alert from "react-bootstrap/Alert"

class Notification extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            open: true,
        }
    }

    onClose = () => {
        this.props.deleteNotification(this.props.index, this.props.notification);
        this.setState((state) => ({
            open: false
        }));
    }

    render() {
        if (this.state.open) {
            return (
                <Alert variant="danger"
                       onClose={this.onClose} dismissible>
                    <Alert.Heading>{this.props.notification.message}</Alert.Heading>
                </Alert>
            );
        } else
            return (<></>);
    }
}


export default connect(null, {deleteNotification})(Notification);