import React from "react";
import {connect} from "react-redux";
import {changeMenuPage, changeNewNotifications, changePage, deleteNotification} from "../redux/actions";
import defaultImg from "../shyam/shyam_close_cropped.jpg";
import Toast from "react-bootstrap/Toast";
import Image from "react-bootstrap/Image";
import {getTimeAgoStr} from "../functions/timeDifference";
import {persistor} from "../redux/store";

class Notification extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            open: true,
            profilePic: "",
        }
        this.getProfilePic();
    }

    onClose = () => {
        this.props.deleteNotification(this.props.index, this.props.notification);
        this.setState((state) => ({
            open: false
        }));
    }

    goToNotification = () => {
        this.onClose();
        this.props.changeNewNotifications(false);
        if (this.props.notification.type === "FRIENDREQUEST") {
            this.props.changeMenuPage("Incoming Friend Requests");
        } else {
            this.props.changePage("MainPage");
        }
    }

    getProfilePic = () => {
        const path = this.props.domainName + "/user/getOthersProfilePic?userId=" +
            this.props.notification.referenceId;
        fetch(path, {
            method: "GET",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache",
            },
            credentials: "include",
        }).then((res) => {
            console.log("status" + res.status);
            if (res.status === 200) {
                res.blob().then((blob) => {
                    let url = window.URL.createObjectURL(blob);
                    this.setState((state) => ({
                        profilePic: url,
                    }));
                });
            } else if (res.status === 302) {
                persistor.purge();
            } else {
                let url = defaultImg;
                this.setState((state) => ({
                    profilePic: url,
                }));
            }
        });
    };

    render() {
        if (this.state.open) {
            return (
                <Toast className="notificationStyle" onClose={this.onClose}>
                    <Toast.Header>
                        <div className="outerDiv" style={{width: "50%"}}>
                            <div className="notificationPic innerDiv ">
                                <Image style={{height: "100%", width: "100%"}} src={this.state.profilePic} roundedCircle
                                       fluid/>
                            </div>
                        </div>
                        <small>{getTimeAgoStr(this.props.notification.timestamp)}</small>
                    </Toast.Header>
                    <Toast.Body>
                        <div className="textStyle"
                             onClick={this.goToNotification}>{this.props.notification.message}</div>
                    </Toast.Body>
                </Toast>

            );
        } else
            return (<></>);
    }
}

function mapStateToProps(state) {
    return {
        domainName: state.domainName,
    };
}

export default connect(mapStateToProps, {
    deleteNotification,
    changeMenuPage,
    changeNewNotifications,
    changePage
})(Notification);