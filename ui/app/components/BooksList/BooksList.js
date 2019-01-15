import React from 'react';
import PropTypes from 'prop-types';

import List from 'components/List';
import ListItem from 'components/ListItem';
import LoadingIndicator from 'components/LoadingIndicator';
import BookListItem from 'containers/BookListItem';

const BooksList = ({ loading, error, books }) => {
  if (loading) {
    return <List component={LoadingIndicator} />;
  }

  if (error !== false) {
    const ErrorComponent = () => (
      <ListItem item={'Something went wrong, please try again!'} />
    );
    return <List component={ErrorComponent} />;
  }

  if (books !== false) {
    return <List items={books} component={BookListItem} />;
  }

  return null;
};

BooksList.propTypes = {
  loading: PropTypes.bool,
  error: PropTypes.any,
  books: PropTypes.any
};

export default BooksList;
