import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { API_URL_DOMAINS, getAllDomains } from '../../api/domain/query';

const DomainListComponent = () => {
    const [domains, setDomains] = useState([]);

    useEffect(() => {
        const fetchDomains = async () => {
            try {
                const response = await getAllDomains()
                setDomains(response.data);
            } catch (error) {
                console.error('Error fetching domains:', error);
            }
        };
        fetchDomains();
    }, []);

    return (
        <div>
            <h1>Domains</h1>
            <ul>
                {domains.map((domain) => (
                    <li key={domain.id}>{domain.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default DomainListComponent;