import React from "react";
import Row from "react-bootstrap/Row";
import Image from "react-bootstrap/Image";
import defaultImg from "../shyam/shyam_close_cropped.jpg";
import Col from "react-bootstrap/Col";

export default class FriendEntry extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      profilePic: "",
    };
  }

  componentDidMount() {
    this.getProfilePic();
  }

  getProfilePic = () => {
    const path =
        this.props.domainName +
        "/user/getOthersProfilePic?userId=" +
        this.props.friend.id;
    fetch(path, {
      method: "GET",
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Cache-Control": "no-cache",
      },
      credentials: "include",
    }).then((res) => {
      if (res.status === 200) {
        res.blob().then((blob) => {
          let url = window.URL.createObjectURL(blob);
          this.setState((state) => ({
            profilePic: url,
          }));
        });
      } else {
        let url = defaultImg;
        this.setState((state) => ({
          profilePic: url,
        }));
      }
    });
  };

  render() {
    return (
        <Row>
          <div className="friendListPic">
            <Image src={this.state.profilePic} roundedCircle fluid/>
          </div>
          <Col xl={9} fluid>
            <div style={{width: "100%", height: "100%"}}>
              <div key="end" className="vertCenterAndCut textStyle">
                {this.props.friend.name}
              </div>
            </div>
          </Col>
        </Row>
    );
  }
}
