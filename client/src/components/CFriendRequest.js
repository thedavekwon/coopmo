import React from "react";

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
                "Content-Type": "application/json"
            },
            credentials: 'include',
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
        headers: {"Access-Control-Allow-Origin": "*", "Cache-Control": "no-cache", "Content-Type": "application/json"},
        credentials: 'include',
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
                  <div
                    className="vector"
                    dangerouslySetInnerHTML={{
                      __html: `<svg preserveAspectRatio="none" width="62" height="64" viewBox="0 0 62 64" fill="none" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
<ellipse cx="31" cy="31.9118" rx="31" ry="31.9118" fill="url(#pattern0)"/>
<defs>
<pattern id="pattern0" patternContentUnits="objectBoundingBox" width="1" height="1">
<use xlink:href="#image0" transform="translate(0 -0.107143) scale(0.00625 0.00607143)"/>
</pattern>
<image id="image0" width="160" height="200" xlink:href="data:image/jpeg;base64,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"/>
</defs>
</svg>
`,
                    }}
                  />
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
