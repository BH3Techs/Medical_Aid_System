import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SponsorAdministration from './sponsor-administration';
import SponsorAdministrationDetail from './sponsor-administration-detail';
import SponsorAdministrationUpdate from './sponsor-administration-update';
import SponsorAdministrationDeleteDialog from './sponsor-administration-delete-dialog';

const SponsorAdministrationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SponsorAdministration />} />
    <Route path="new" element={<SponsorAdministrationUpdate />} />
    <Route path=":id">
      <Route index element={<SponsorAdministrationDetail />} />
      <Route path="edit" element={<SponsorAdministrationUpdate />} />
      <Route path="delete" element={<SponsorAdministrationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SponsorAdministrationRoutes;
