import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './document.reducer';

export const DocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documentEntity = useAppSelector(state => state.document.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.document.detail.title">Document</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentEntity.id}</dd>
          <dt>
            <span id="format">
              <Translate contentKey="medicalAidSystemApp.document.format">Format</Translate>
            </span>
          </dt>
          <dd>{documentEntity.format}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.document.name">Name</Translate>
            </span>
          </dt>
          <dd>{documentEntity.name}</dd>
          <dt>
            <span id="ownerType">
              <Translate contentKey="medicalAidSystemApp.document.ownerType">Owner Type</Translate>
            </span>
          </dt>
          <dd>{documentEntity.ownerType}</dd>
          <dt>
            <span id="dateCreated">
              <Translate contentKey="medicalAidSystemApp.document.dateCreated">Date Created</Translate>
            </span>
          </dt>
          <dd>
            {documentEntity.dateCreated ? <TextFormat value={documentEntity.dateCreated} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="validity">
              <Translate contentKey="medicalAidSystemApp.document.validity">Validity</Translate>
            </span>
          </dt>
          <dd>{documentEntity.validity ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.document.documentType">Document Type</Translate>
          </dt>
          <dd>{documentEntity.documentType ? documentEntity.documentType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document/${documentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentDetail;
