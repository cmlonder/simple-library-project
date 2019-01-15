import React from 'react';
import PropTypes from 'prop-types';
import { Helmet } from 'react-helmet';
import BooksList from 'components/BooksList';
import './style.scss';

export default class HomePage extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function
  componentDidMount() {
    if (!this.props.books) {
      this.props.onSubmitForm();
    }
  }

  render() {
    const { loading, error, books } = this.props;
    const booksListProps = {
      loading,
      error,
      books,
    };

    return (
      <article>
        <Helmet>
          <title>Home Page</title>
          <meta name="description" content="Library Homepage" />
        </Helmet>
        <div className="home-page">
          <section className="centered">
            <h2>Showing all books in the library</h2>
          </section>
          <section>
            <form onSubmit={this.props.onSubmitForm}>
              <button
                id="getBooks"
                type="button"
                value="Get Books"
                onChange={this.props.onClickGetBooks}
              />
            </form>
            <BooksList {...booksListProps} />
          </section>
        </div>
      </article>
    );
  }
}

HomePage.propTypes = {
  loading: PropTypes.bool,
  error: PropTypes.oneOfType([
    PropTypes.object,
    PropTypes.bool,
  ]),
  books: PropTypes.oneOfType([
    PropTypes.array,
    PropTypes.bool,
  ]),
  onClickGetBooks: PropTypes.func,
  onSubmitForm: PropTypes.func,
};
