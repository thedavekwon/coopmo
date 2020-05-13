import React from "react";
import CFeedItemPayment from "./CFeedItemPayment.js";
import CFeedItemBank from "./CFeedItemBank.js";
import CFeedItemEmpty from "./CFeedItemEmpty.js";
import {fetchFeed, fetchFeedFrom} from "../functions/fetchFeed";

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
    var shouldReverse = false;
    switch (feedTab) {
      case "Me":
        fetch_type = "Private";
        shouldReverse = true;
        break;
      case "Friend":
        fetch_type = "Friend";
        shouldReverse = true;
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
          const feedItemDatas = response.data;
          if (shouldReverse) {
            this.setState({
              feedItemDatas: this.state.feedItemDatas.concat(feedItemDatas.reverse()),
            });
          } else {
            this.setState({
              feedItemDatas: this.state.feedItemDatas.concat(feedItemDatas),
            });
          }

        }
      });
  }

  addNewItemsToList(feedTab) {
    const oldestTimestampStr = this.state.feedItemDatas[this.state.feedItemDatas.length -1].timestamp;
    console.log(oldestTimestampStr);
    var fetch_type = "";
    var shouldReverse = false;
    switch (feedTab) {
      case "Me":
        fetch_type = "Private";
        break;
      case "Friend":
        fetch_type = "Friend";
        shouldReverse = true;
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
          if (shouldReverse) {
            this.setState({
              feedItemDatas: this.state.feedItemDatas.concat(feedItemDatas.reverse()),
            });
          } else {
            this.setState({
              feedItemDatas: this.state.feedItemDatas.concat(feedItemDatas),
            });
          }
        }
      });
  }

  handleScroll = () => {
    const scrollTop = this.myRef.current.scrollTop;
    const scrollHeight = this.myRef.current.scrollHeight;
    const clientHeight = this.myRef.current.clientHeight;
    // debounce(() => {
      if (
        scrollHeight - scrollTop === clientHeight && this.state.feedItemDatas.length > 1
      ) {
        this.addNewItemsToList(this.props.feedTab);
      }
    // }, 100);
  }

  /*
  componentDidMount() {
    this.updateList(this.props.feedTab);
  }
  */
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
        <CFeedItemEmpty key={"0"}/>
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
          overflowY: "auto",
          height: "100%"
        }} onScroll={this.handleScroll}
      >
        {feedItems}
      </div>
    );
  }
}
