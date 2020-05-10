import React from "react";
//need to test request updating again
import Alert from "react-bootstrap/Alert";

export default class FormAlert extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        if (this.props.showMessage) {
            return (
                <Alert variant={this.props.messageType === "ERROR" ? "danger" : "primary"}
                       onClose={this.props.onClose} dismissible>
                    <Alert.Heading>{this.props.messageType}</Alert.Heading>
                    <p>
                        {this.props.message}
                    </p>
                </Alert>
            );
        } else
            return (<></>);

    }

}