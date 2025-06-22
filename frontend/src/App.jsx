import { useState } from 'react';
import axios from 'axios';
import { Container, Typography, Box } from '@mui/material';
import FilePicker from './components/FilePicker';
import ResultGrid from './components/ResultGrid';
import SummaryCard from './components/SummaryCard';

// set once; change the port if you moved Spring Boot
axios.defaults.baseURL = 'http://localhost:8080/api';

export default function App() {
  const [records, setRecords] = useState([]);
  const [topPair, setTopPair] = useState(null);

  const handleUploadSuccess = (data) => {
    setRecords(data.records);
    setTopPair(data.topPair);
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        Employee-Pair Collaboration Analyzer
      </Typography>

      <FilePicker onSuccess={handleUploadSuccess} />

      {topPair && <SummaryCard topPair={topPair} sx={{ mt: 3 }} />}

      {records.length > 0 && (
        <Box sx={{ mt: 3 }}>
          <ResultGrid rows={records} />
        </Box>
      )}
    </Container>
  );
}
