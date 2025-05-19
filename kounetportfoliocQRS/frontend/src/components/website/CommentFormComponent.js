import React, { useState } from 'react';

const CommentFormComponent = () => {
  const [form, setForm] = useState({
    name: '',
    email: '',
    website: '',
    comment: '',
  });
  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Here you would typically send the data to your backend
    // e.g., await postComment(form);
    setSubmitted(true);
    setForm({
      name: '',
      email: '',
      website: '',
      comment: '',
    });
    setTimeout(() => setSubmitted(false), 3000); // Hide message after 3s
  };

  return (
    <section id="comment-form" className="comment-form section">
      <div className="container">
        <form onSubmit={handleSubmit}>
          <h4>Post Comment</h4>
          <p>Your email address will not be published. Required fields are marked * </p>
          <div className="row">
            <div className="col-md-6 form-group">
              <input
                name="name"
                type="text"
                className="form-control"
                placeholder="Your Name*"
                value={form.name}
                onChange={handleChange}
                required
              />
            </div>
            <div className="col-md-6 form-group">
              <input
                name="email"
                type="email"
                className="form-control"
                placeholder="Your Email*"
                value={form.email}
                onChange={handleChange}
                required
              />
            </div>
          </div>
          <div className="row">
            <div className="col form-group">
              <input
                name="website"
                type="text"
                className="form-control"
                placeholder="Your Website"
                value={form.website}
                onChange={handleChange}
              />
            </div>
          </div>
          <div className="row">
            <div className="col form-group">
              <textarea
                name="comment"
                className="form-control"
                placeholder="Your Comment*"
                value={form.comment}
                onChange={handleChange}
                required
                rows={4}
              ></textarea>
            </div>
          </div>
          <div className="text-center">
            <button type="submit" className="btn btn-primary">Post Comment</button>
          </div>
          {submitted && (
            <div className="alert alert-success mt-3">
              Thank you! Your comment has been submitted.
            </div>
          )}
        </form>
      </div>
    </section>
  );
};

export default CommentFormComponent;