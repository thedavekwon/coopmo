import React from "react";
import {connect} from "react-redux";
import {deleteNotification} from "../redux/actions";
import {getTimeAgoStr} from "../functions/timeDifference";
import defaultImg from "../shyam/shyam_close_cropped.jpg";
import Toast from "react-bootstrap/Toast";
import Image from "react-bootstrap/Image";

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
            console.log(this.props.notification);
            return (
                <Toast onClose={this.onClose}>
                    <Toast.Header>
                        <div className="friendListPic innerDiv ">
                            <Image src={defaultImg} roundedCircle fluid/>
                        </div>
                        <small>{getTimeAgoStr(this.props.notification.timestamp)}</small>
                    </Toast.Header>
                    <Toast.Body>{this.props.notification.message}</Toast.Body>
                </Toast>
                /*
                  <Alert variant="danger"
                         onClose={this.onClose} dismissible>
                      <Alert.Heading>{this.props.notification.message}</Alert.Heading>
                  </Alert>
                  */
            );
        } else
            return (<></>);
    }
}


export default connect(null, {deleteNotification})(Notification);