import { useState } from 'react';
import axios from 'axios';
import { Button, LinearProgress, Alert } from '@mui/material';

export default function FilePicker({ onSuccess }) {
  const [loading, setLoading] = useState(false);
  const [error, setError]   = useState(null);

  const handleChange = async (e) => {
    if (!e.target.files?.length) return;
    const file = e.target.files[0];
    const form = new FormData();
    form.append('file', file);

    setLoading(true);
    setError(null);

    try {
      const res = await axios.post('/upload', form);
      onSuccess(res.data);
    } catch (err) {
      const msg =
        err.response?.data?.message ||
        err.response?.data?.error   ||
        err.message;
      setError(msg);
      onSuccess({ records: [], topPair: null }); // clear table
    } finally {
      setLoading(false);
      e.target.value = ''; // reset so same file can be re-selected
    }
  };

  return (
    <>
      <Button variant="contained" component="label">
        Choose CSV
        <input type="file" accept=".csv" hidden onChange={handleChange} />
      </Button>

      {loading && <LinearProgress sx={{ mt: 2 }} />}

      {error && (
        <Alert severity="error" sx={{ mt: 2 }}>
          {error}
        </Alert>
      )}
    </>
  );
}
