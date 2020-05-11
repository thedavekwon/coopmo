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
    const path = this.props.domainName + "/user/getOthersProfilePic?userId=" + this.props.friend.id;
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
          <Col xl={3}>
            <div className="innerDiv friendListPic">
              <Image src={this.state.profilePic} roundedCircle fluid/>
            </div>
          </Col>
          <Col xl={9} fluid>
            <div style={{width: "100%", height: "100%"}}>
              <div style={{
                color: "rgba(0, 0, 0, 1)",
                fontSize: 24,
                fontWeight: 400,
                fontFamily: "Muli",
                textAlign: "LEFT",
                fontStyle: "normal",
                lineHeight: "125%",
                letterSpacing: "0px",
              }} key="end"
                   className="innerDiv vertCenterAndCut">
                {this.props.friend.name}
              </div>
            </div>

            {//this.props.friend.name}
            }
          </Col>
          {/*
        <div style={{ zIndex: 2 }} className="outerDiv centerer">
          <div
            id="I38:1056;14:12;11:0"
            style={{
              marginLeft: 73.054443359375,
              marginRight: 9.80010986328125,
              flexGrow: 1,
              marginTop: 13,
              marginBottom: 13,
              color: "rgba(0, 0, 0, 1)",
              fontSize: 24,
              fontWeight: 400,
              fontFamily: "Muli",
              textAlign: "RIGHT",
              fontStyle: "normal",
              lineHeight: "125%",
              letterSpacing: "0px",
            }}
            className="innerDiv"
          >
            <div>
              <span style={{}} key="end">
                {this.props.friend.name}
              </span>
            </div>
          </div>
        </div>
        */}
        </Row>
        /*
              <div style={{ zIndex: key + 1 }} className="outerDiv centerer" key={friend}>
                <div
                  id="I38:1056;14:12"
                  style={{
                    flexGrow: 1,
                    height: "10%",
                    top: (key + 1) * 10 + "%",
                    backgroundColor: "rgba(0, 0, 0, 0)",
                    overflow: "hidden",
                  }}
                  className="innerDiv"
                >
                  <div>
                    <div style={{}} className="outerDiv centerer">
                      <div
                        id="I38:1056;14:12;10:3"
                        style={{
                          marginLeft: 0,
                          marginRight: 0,
                          flexGrow: 1,
                          marginTop: 0,
                          marginBottom: 0,
                          backgroundColor: "rgba(255, 255, 255, 1)",
                        }}
                        className="innerDiv"
                      >
                        <div></div>
                      </div>
                    </div>
                    <div style={{ zIndex: 1 }} className="outerDiv centerer">
                      <div
                        id="I38:1056;14:12;10:6"
                        style={{
                          marginLeft: 16.92724609375,
                          marginRight: "80%",
                          flexGrow: 1,
                          marginTop: 2,
                          marginBottom: 3,
                        }}
                        className="innerDiv"
                      >
                        <div
                        />
                      </div>
                    </div>
                    <div style={{ zIndex: 2 }} className="outerDiv centerer">
                      <div
                        id="I38:1056;14:12;11:0"
                        style={{
                          marginLeft: 73.054443359375,
                          marginRight: 9.80010986328125,
                          flexGrow: 1,
                          marginTop: 13,
                          marginBottom: 13,
                          color: "rgba(0, 0, 0, 1)",
                          fontSize: 24,
                          fontWeight: 400,
                          fontFamily: "Muli",
                          textAlign: "RIGHT",
                          fontStyle: "normal",
                          lineHeight: "125%",
                          letterSpacing: "0px",
                        }}
                        className="innerDiv"
                      >
                        <div>
                          <span style={{}} key="end">
                            {friend.name}
                          </span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              */
    );
  }
}
