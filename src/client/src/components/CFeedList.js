import React from "react";
import CFeedItemPayment from "./CFeedItemPayment.js";
import CFeedItemBank from "./CFeedItemBank.js";
import CFeedItemEmpty from "./CFeedItemEmpty.js";
import {fetchFeed, fetchFeedFrom} from "../functions/fetchFeed";
import debounce from "lodash.debounce";

export default class CFeedList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      feedItemDatas: [],
    };
    this.myRef = React.createRef()
  }

  // each payment item has following:
  // id, amount, type, timestamp, fromUser, toUser, likes

  updateList(feedTab) {
    var fetch_type = "";
    switch (feedTab) {
      case "Me":
        fetch_type = "Private";
        break;
      case "Friend":
        fetch_type = "Friend";
        break;
      case "Public":
        fetch_type = "Public";
        break;
      default:
        fetch_type = "(ERROR, SHOULD NOT SEE THIS)";
    }
    fetchFeed(this.props.domainName, fetch_type)
      .then((response) => response.json())
      .then((response) => {
        console.log(response);
        if (response.data) {
          // const feedItemDatas = response.data.filter(d => d.type === fetch_type.toUpperCase());
          const feedItemDatas = response.data;
          this.setState({
            feedItemDatas: this.state.feedItemDatas.concat(feedItemDatas.reverse()),
          });
        }
      });
  }

  addNewItemsToList(feedTab) {
    const oldestTimestampStr = this.state.feedItemDatas[this.state.feedItemDatas.length -1].timestamp;
    console.log(oldestTimestampStr);
    var fetch_type = "";
    switch (feedTab) {
      case "Me":
        fetch_type = "Private";
        break;
      case "Friend":
        fetch_type = "Friend";
        break;
      case "Public":
        fetch_type = "Public";
        break;
      default:
        fetch_type = "(ERROR, SHOULD NOT SEE THIS)";
    }
    fetchFeedFrom(this.props.domainName, fetch_type, oldestTimestampStr)
      .then((response) => response.json())
      .then((response) => {
        console.log(response);
        if (response.data) {
          const feedItemDatas = response.data;
          this.setState({
            feedItemDatas: this.state.feedItemDatas.concat(feedItemDatas.reverse()),
          });
        }
      });
  }

  handleScroll = () => {
    const scrollTop = this.myRef.current.scrollTop;
    const scrollHeight = this.myRef.current.scrollHeight;
    const clientHeight = this.myRef.current.clientHeight;
    // debounce(() => {
      if (
        scrollHeight - scrollTop === clientHeight && this.state.feedItemDatas.length !== 0
      ) {
        this.addNewItemsToList(this.props.feedTab);
      }
    // }, 100);
  }

  componentDidMount() {
    this.updateList(this.props.feedTab);
  }

  componentDidUpdate(prevProps) {
    if (this.props.feedTab !== prevProps.feedTab) {
      this.setState({
        feedItemDatas: [],
      });
      this.updateList(this.props.feedTab);
    }
  }

  render() {
    const payments = this.state.feedItemDatas;
    const feedItems = [];
    if (payments.length===0) {
      feedItems.push(
        <CFeedItemEmpty />
      )
    } else {
        for (let ii = 1; ii <=  payments.length; ii++) {
          var payment = payments[ii - 1];
          if (payment.type === "IN" || payment.type === "OUT") {
            feedItems.push(
                <CFeedItemBank
                    tab={this.props.feedTab}
                    listIndex={ii}
                    key={ii.toString()}
                    type={payment.type}
                    amount={payment.amount}
                    timestamp={payment.timestamp}
                />
            );
          } else {
            feedItems.push(
              <CFeedItemPayment
                tab={this.props.feedTab}
                listIndex={ii}
                domainName={this.props.domainName}
                key={ii.toString()}
                transactionId={payment.id}
                fromUserId={payment.fromUser.id}
                fromUserHandle={payment.fromUser.handle}
                toUserHandle={payment.toUser.handle}
                type={payment.type}
                amount={payment.amount}
                timestamp={payment.timestamp}
                comment={payment.comment}
                likes={payment.likes}
                username={this.props.username}
              />
            );
          }
        }
    }
    

    return (
      <div
        ref={this.myRef}
        style={{
          overflowY: "scroll",
          maxHeight: "600px",
        }} onScroll={this.handleScroll}
      >
        {feedItems}
      </div>
    );
  }
}
