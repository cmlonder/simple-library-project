import React from 'react';
import PropTypes from 'prop-types';
import ListItem from 'components/ListItem';
import './style.scss';

export default class BookListItem extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function
  render() {
    const { item } = this.props;

    const content = (
      <div className="book-list-item">
        <a className="book-list-item__name">
          {item.name}
        </a>
        <a className="book-list-item__authorName">
          {item.authorName}
        </a>
      </div>
    );

    return (
      <ListItem key={`book-list-item-${item.full_name}`} item={content} />
    );
  }
}

BookListItem.propTypes = {
  item: PropTypes.object,
};
