import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './document-type.reducer';

export const DocumentTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documentTypeEntity = useAppSelector(state => state.documentType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentTypeDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.documentType.detail.title">DocumentType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentTypeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.documentType.name">Name</Translate>
            </span>
          </dt>
          <dd>{documentTypeEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="medicalAidSystemApp.documentType.description">Description</Translate>
            </span>
          </dt>
          <dd>{documentTypeEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/document-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document-type/${documentTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentTypeDetail;
