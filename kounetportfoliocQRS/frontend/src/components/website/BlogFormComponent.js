import React, { useState } from 'react';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import {
    TextField,
    Button,
    Typography,
    Box,
    Stack
} from '@mui/material';
import { createArticle, updateArticle } from '../../api/blog/command';

const ArticleSchema = Yup.object().shape({
    title: Yup.string().min(5).max(100).required('Title is required'),
    content: Yup.string().min(10).max(1000).required('Content is required'),
    urlMedia: Yup.string().required('Media URL is required'),
    createdAt: Yup.string().required('Date is required'),
    authorId: Yup.string().min(1).max(50).required('Author ID is required'),
    domainId: Yup.string().min(1).max(50).required('Domain ID is required'),
    tagIds: Yup.string().required('At least one tag is required'),
    commentIds: Yup.string().required('At least one comment is required'),
});

const BlogFormComponent = ({ articleId, initialData }) => {
    const [previewUrl, setPreviewUrl] = useState(initialData?.urlMedia || '');
    const [selectedFile, setSelectedFile] = useState(null);

    const defaultInitialValues = {
        title: '',
        content: '',
        urlMedia: '',
        createdAt: new Date().toISOString().split('T')[0], // "YYYY-MM-DD"
        authorId: '',
        domainId: '',
        tagIds: '',
        commentIds: ''
    };

    const handleSubmit = async (values, { setSubmitting, resetForm }) => {
        // Prepare tagIds and commentIds as arrays
        const tagArray = values.tagIds.split(',').map(t => t.trim()).filter(Boolean);
        const commentArray = values.commentIds.split(',').map(c => c.trim()).filter(Boolean);

        // If you manage file uploads and want to convert to url:
        let urlMediaValue = values.urlMedia;
        if (selectedFile) {
            // Here, upload the file and get the URL, then:
            // urlMediaValue = await uploadFileAndGetUrl(selectedFile);
            // For now, just use previewUrl for demonstration:
            urlMediaValue = previewUrl;
        }

        const articleData = {
            ...values,
            createdAt: values.createdAt, // keep as string, backend should parse
            urlMedia: urlMediaValue,
            tagIds: tagArray,
            commentIds: commentArray,
        };

        try {
            if (articleId) {
                await updateArticle(articleId, articleData);
                alert('Article updated successfully!');
            } else {
                await createArticle(articleData);
                alert('Article created successfully!');
                resetForm();
                setPreviewUrl('');
                setSelectedFile(null);
            }
        } catch (error) {
            console.error('Error saving article:', error);
            alert('Error saving article.');
        } finally {
            setSubmitting(false);
        }
    };

    const handleFileChange = (e, setFieldValue) => {
        const file = e.target.files[0];
        if (file) {
            setSelectedFile(file);
            const localUrl = URL.createObjectURL(file);
            setPreviewUrl(localUrl);
            setFieldValue('urlMedia', localUrl); // set preview as urlMedia for now
        }
    };

    return (
        <Box sx={{ maxWidth: 600, mx: 'auto', mt: 4 }}>
            <Typography variant="h5" gutterBottom>
                {articleId ? 'Update' : 'Create'} Article
            </Typography>
            <Formik
                initialValues={initialData || defaultInitialValues}
                validationSchema={ArticleSchema}
                onSubmit={handleSubmit}
            >
                {({ values, errors, touched, handleChange, setFieldValue, isSubmitting }) => (
                    <Form>
                        <Stack spacing={2}>
                            <TextField
                                label="Title"
                                name="title"
                                value={values.title}
                                onChange={handleChange}
                                error={touched.title && Boolean(errors.title)}
                                helperText={touched.title && errors.title}
                            />
                            <TextField
                                label="Content"
                                name="content"
                                multiline
                                rows={4}
                                value={values.content}
                                onChange={handleChange}
                                error={touched.content && Boolean(errors.content)}
                                helperText={touched.content && errors.content}
                            />
                            <TextField
                                label="Media URL"
                                name="urlMedia"
                                value={values.urlMedia}
                                onChange={handleChange}
                                error={touched.urlMedia && Boolean(errors.urlMedia)}
                                helperText={touched.urlMedia && errors.urlMedia}
                            />
                            <Button
                                variant="outlined"
                                component="label"
                            >
                                Upload Image
                                <input
                                    type="file"
                                    hidden
                                    accept="image/*"
                                    onChange={e => handleFileChange(e, setFieldValue)}
                                />
                            </Button>
                            {previewUrl && (
                                <Box>
                                    <Typography variant="subtitle2">Image Preview:</Typography>
                                    <img
                                        src={previewUrl}
                                        alt="Preview"
                                        style={{ width: '100%', maxHeight: 300, objectFit: 'cover' }}
                                    />
                                </Box>
                            )}
                            <TextField
                                label="Created At"
                                name="createdAt"
                                type="date"
                                value={values.createdAt}
                                onChange={handleChange}
                                error={touched.createdAt && Boolean(errors.createdAt)}
                                helperText={touched.createdAt && errors.createdAt}
                                InputLabelProps={{ shrink: true }}
                            />
                            <TextField
                                label="Author ID"
                                name="authorId"
                                value={values.authorId}
                                onChange={handleChange}
                                error={touched.authorId && Boolean(errors.authorId)}
                                helperText={touched.authorId && errors.authorId}
                            />
                            <TextField
                                label="Domain ID"
                                name="domainId"
                                value={values.domainId}
                                onChange={handleChange}
                                error={touched.domainId && Boolean(errors.domainId)}
                                helperText={touched.domainId && errors.domainId}
                            />
                            <TextField
                                label="Tag IDs (comma separated)"
                                name="tagIds"
                                value={values.tagIds}
                                onChange={handleChange}
                                error={touched.tagIds && Boolean(errors.tagIds)}
                                helperText={touched.tagIds && errors.tagIds}
                            />
                            <TextField
                                label="Comment IDs (comma separated)"
                                name="commentIds"
                                value={values.commentIds}
                                onChange={handleChange}
                                error={touched.commentIds && Boolean(errors.commentIds)}
                                helperText={touched.commentIds && errors.commentIds}
                            />
                            <Button variant="contained" type="submit" disabled={isSubmitting}>
                                {articleId ? 'Update' : 'Create'} Article
                            </Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </Box>
    );
};

export default BlogFormComponent;