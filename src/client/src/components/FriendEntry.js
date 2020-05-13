import React from "react";
import defaultImg from "../shyam/shyam_close_cropped.jpg";
import {persistor} from "../redux/store";

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
    return (
        // <Row>
        //   <div className="outerDiv" style={{width: "25%"}}>
        //     <div className="innerDiv friendListPic">
        //       <Image style={{width: "100%", height: "100%"}} src={this.state.profilePic} roundedCircle fluid/>
        //     </div>
        //   </div>

        //   <Col xl={9} fluid>
        //     <div style={{width: "100%", height: "100%"}}>
        //       <div key="end" className="innerDiv vertCenterAndCut textStyle">
        //         {this.props.friend.name}
        //       </div>
        //     </div>
        //   </Col>
        // </Row>
        
            <div>
            <img src={this.state.profilePic} style={{
                marginLeft: "-10px",
                borderRadius:"50%",
                }} className="friendListPic" alt="friendListPic"/>
            <span className="textStyle">
            {this.props.friend.name}
            </span>
          </div>
         
    );
  }
}
