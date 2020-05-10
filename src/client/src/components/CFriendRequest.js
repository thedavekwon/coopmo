import React from "react";
import {MINH_PIC_LINK} from "../minhPic.js";

export class CFriendRequest extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            request: {
                friendId: this.props.friendId,
            },
            accepted: false,
            declined: false,
        };
    }

    acceptRequest = () => {
        const path = this.props.domainName + "/user/acceptIncomingRequest";
        fetch(path, {
            method: "POST",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache",
                "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify(this.state.request),
        })
            .then((res) => res.json())
            .then(
                (result) => {
                    if (result.error != null) {
                        console.log(result.error);
                    } else {
                        this.setState((state) => ({
                            accepted: true,
                        }));
                    }
                },
                (error) => {
                    console.log("error sending request");
                }
            );
    console.log("in accept request");
  };

  declineRequest = () => {
      const path = this.props.domainName + "/user/declineFriendRequest";
    fetch(path, {
        method: "POST",
        headers: {
            "Access-Control-Allow-Origin": "*",
            "Cache-Control": "no-cache",
            "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify(this.state.request),
    })
      .then((res) => res.json())
      .then(
        (result) => {
          if (result.error != null) {
            console.log(result.error);
          } else {
            this.setState((state) => ({
              declined: true,
            }));
          }
        },
        (error) => {
          console.log("error sending request");
        }
      );
    console.log("in decline request");
  };

  render() {
    let acceptButton;
    let declineButton;

    if (!this.state.accepted) {
      declineButton = (
        <div>
          <div
            style={{
              zIndex: 4,
              alignItems: "center",
            }}
            className="outerDiv centerer"
          >
            <div
              id="I38:417;35:532"
              style={{
                width: "29.41176470588235%",
                marginLeft: "65.88235294117646%",
                height: 28,
                marginTop: 52,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:417;35:533"
                    style={{
                      width: "100%",
                      marginLeft: "0%",
                      height: "100%",
                      top: "0%",
                      backgroundColor: "rgba(237, 75, 75, 1)",
                      borderRadius: "8px 8px 8px 8px",
                    }}
                    className="innerDiv"
                    onClick={!this.state.accepted ? this.declineRequest : null}
                  >
                    <div> </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div
            style={{
              zIndex: 5,
              alignItems: "center",
            }}
            className="outerDiv centerer"
          >
            <div
              id="I38:417;35:534"
              style={{
                width: "29.41176470588235%",
                marginLeft: "65.88235294117646%",
                height: 20.5,
                marginTop: 53.5,
                color: "rgba(255, 255, 255, 1)",
                fontSize: 16,
                fontWeight: 600,
                fontFamily: "Muli",
                textAlign: "CENTER",
                fontStyle: "normal",
                lineHeight: "125%",
                letterSpacing: "0px",
              }}
              className="innerDiv"
              onClick={!this.state.accepted ? this.declineRequest : null}
            >
              <div>
                <span style={{}} key="end">
                  {this.state.declined ? "Declined" : "Decline"}
                </span>
              </div>
            </div>
          </div>
          <div
            style={{
              zIndex: 6,
              alignItems: "center",
            }}
            className="outerDiv centerer"
          >
            <div
              id="I38:417;35:535"
              style={{
                width: 16,
                marginLeft: "70%",
                height: 16,
                marginTop: 52,
              }}
              className="innerDiv"
              onClick={!this.state.accepted ? this.declineRequest : null}
            >
              <div
                className="vector"
                dangerouslySetInnerHTML={{
                  __html: `<svg preserveAspectRatio="none" width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd" clip-rule="evenodd" d="M0 8C0 3.58172 3.58172 0 8 0C12.4163 0.0047399 15.9953 3.58369 16 8C16 12.4183 12.4183 16 8 16C3.58172 16 0 12.4183 0 8ZM2 8C2 11.3137 4.68629 14 8 14C11.3122 13.9964 13.9964 11.3122 14 8C14 4.68629 11.3137 2 8 2C4.68629 2 2 4.68629 2 8Z" fill="white"/>
                <path fill-rule="evenodd" clip-rule="evenodd" d="M10.707 5.293C10.5195 5.10539 10.2652 4.99997 9.99998 4.99997C9.73477 4.99997 9.48043 5.10539 9.29298 5.293L7.99998 6.5859L6.70698 5.293C6.31461 4.91403 5.6909 4.91945 5.30517 5.30519C4.91943 5.69092 4.91401 6.31463 5.29298 6.707L6.58588 8L5.29298 9.293C5.03303 9.54408 4.92877 9.91588 5.02029 10.2655C5.1118 10.6151 5.38485 10.8882 5.73448 10.9797C6.08411 11.0712 6.45591 10.967 6.70698 10.707L7.99998 9.4141L9.29298 10.707C9.68536 11.086 10.3091 11.0806 10.6948 10.6948C11.0805 10.3091 11.086 9.68538 10.707 9.293L9.41408 8L10.707 6.707C10.8946 6.51956 11 6.26522 11 6C11 5.73479 10.8946 5.48045 10.707 5.293Z" fill="white"/>
                </svg>
                `,
                }}
              />
            </div>
          </div>
        </div>
      );
    }

    if (!this.state.declined) {
      acceptButton = (
        <div>
          <div
            style={{
              alignItems: "center",
            }}
            className="outerDiv centerer"
          >
            <div
              id="I38:417;35:525"
              style={{
                width: "29.41176470588235%",
                marginLeft: "65.88235294117646%",
                height: 28,
                marginTop: -46,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:417;35:526"
                    style={{
                      width: "100%",
                      marginLeft: "0%",
                      height: "100%",
                      top: "0%",
                      backgroundColor: "rgba(12, 200, 99, 1)",
                      borderRadius: "8px 8px 8px 8px",
                    }}
                    className="innerDiv"
                    onClick={!this.state.accepted ? this.acceptRequest : null}
                  >
                    <div> </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div
            style={{
              zIndex: 1,
              alignItems: "center",
            }}
            className="outerDiv centerer"
          >
            <div
              id="I38:417;35:527"
              style={{
                width: "29.41176470588235%",
                marginLeft: "65.88235294117646%",
                height: 20.5,
                marginTop: -44.5,
                color: "rgba(255, 255, 255, 1)",
                fontSize: 16,
                fontWeight: 600,
                fontFamily: "Muli",
                textAlign: "CENTER",
                fontStyle: "normal",
                lineHeight: "125%",
                letterSpacing: "0px",
              }}
              className="innerDiv"
              onClick={!this.state.accepted ? this.acceptRequest : null}
            >
              <div>
                <span style={{}} key="end">
                  {this.state.accepted ? "Accepted" : "Accept"}
                </span>
              </div>
            </div>
          </div>
          <div
            style={{
              zIndex: 2,
              alignItems: "center",
            }}
            className="outerDiv centerer"
          >
            <div
              id="I38:417;38:411"
              style={{
                width: 16,
                marginLeft: "70%",
                height: 16,
                marginTop: -46,
              }}
              className="innerDiv"
              onClick={!this.state.accepted ? this.acceptRequest : null}
            >
              <div
                className="vector"
                dangerouslySetInnerHTML={{
                  __html: `<svg preserveAspectRatio="none" width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd" clip-rule="evenodd" d="M9.73243 5.3594L7.16603 8.4375L6.26763 7.36C6.03898 7.08576 5.68139 6.95434 5.32957 7.01524C4.97775 7.07613 4.68514 7.3201 4.56197 7.65524C4.4388 7.99037 4.50378 8.36576 4.73243 8.64L6.39843 10.64C6.58799 10.8682 6.86917 11.0002 7.16579 11.0004C7.46242 11.0006 7.74378 10.8689 7.93363 10.641L11.2676 6.641C11.5021 6.36738 11.5712 5.98878 11.4486 5.64996C11.3259 5.31114 11.0305 5.0645 10.6752 5.00435C10.3199 4.9442 9.95977 5.07983 9.73243 5.3594Z" fill="white"/>
                <path fill-rule="evenodd" clip-rule="evenodd" d="M0 8C0 3.58172 3.58172 0 8 0C12.4163 0.0047399 15.9953 3.58369 16 8C16 12.4183 12.4183 16 8 16C3.58172 16 0 12.4183 0 8ZM2 8C2 11.3137 4.68629 14 8 14C11.3122 13.9964 13.9964 11.3122 14 8C14 4.68629 11.3137 2 8 2C4.68629 2 2 4.68629 2 8Z" fill="white"/>
                </svg>
                `,
                }}
              />
            </div>
          </div>
        </div>
      );
    }
    return (
      <div>
        {acceptButton}
        {declineButton}
        <div
          style={{
            zIndex: 8,
          }}
          className="outerDiv centerer"
        >
          <div
            id="I38:417;38:413"
            style={{
              marginLeft: 8,
              marginRight: "40%",
              flexGrow: 1,
              marginTop: 25,
              marginBottom: 25,
              backgroundColor: "rgba(0, 0, 0, 0)",
            }}
            className="innerDiv"
          >
            <div>
              <div style={{}} className="outerDiv centerer">
                <div
                  id="I38:417;38:413;10:3"
                  style={{
                    marginRight: 0,
                    flexGrow: 1,
                    marginTop: 0,
                    marginBottom: 0,
                  }}
                  className="innerDiv"
                >
                  <div> </div>
                </div>
              </div>
              <div
                style={{
                  zIndex: 1,
                }}
                className="outerDiv centerer"
              >
                <div
                  id="I38:417;38:413;10:6"
                  style={{
                    marginLeft: 23.49072265625,
                    marginRight: "80%",
                    flexGrow: 1,
                    marginTop: 2.54541015625,
                    marginBottom: 3.8182296752929688,
                  }}
                  className="innerDiv"
                >
                  <div>
                    <img src={MINH_PIC_LINK} style={{
                      width: 64,
                      height: 64,
                      borderRadius:"50%"
                      }} />
                  </div>
                  
                </div>
              </div>
              <div
                style={{
                  zIndex: 2,
                }}
                className="outerDiv centerer"
              >
                <div
                  id="I38:417;38:413;11:0"
                  style={{
                    marginLeft: 101.3818359375,
                    marginRight: 13.5999755859375,
                    flexGrow: 1,
                    marginTop: 16.54541015625,
                    marginBottom: 16.545501708984375,
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
                      {this.props.name}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div
          style={{
            zIndex: 9,
          }}
          className="outerDiv centerer"
        >
          <div
            id="I38:417;38:535"
            style={{
              marginLeft: 0,
              marginRight: 0,
              flexGrow: 1,
              marginTop: 0,
              height: 1,
              backgroundColor: "rgba(102, 0, 153, 1)",
            }}
            className="innerDiv"
          >
            <div></div>
          </div>
        </div>
      </div>
    );
  }
}
