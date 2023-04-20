import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ContactDetails from './contact-details';
import ContactDetailsDetail from './contact-details-detail';
import ContactDetailsUpdate from './contact-details-update';
import ContactDetailsDeleteDialog from './contact-details-delete-dialog';

const ContactDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ContactDetails />} />
    <Route path="new" element={<ContactDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<ContactDetailsDetail />} />
      <Route path="edit" element={<ContactDetailsUpdate />} />
      <Route path="delete" element={<ContactDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ContactDetailsRoutes;
