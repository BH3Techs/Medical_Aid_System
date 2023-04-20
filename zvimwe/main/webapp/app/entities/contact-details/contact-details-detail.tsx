import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contact-details.reducer';

export const ContactDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contactDetailsEntity = useAppSelector(state => state.contactDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactDetailsDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.contactDetails.detail.title">ContactDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contactDetailsEntity.id}</dd>
          <dt>
            <span id="primaryPhoneNumber">
              <Translate contentKey="medicalAidSystemApp.contactDetails.primaryPhoneNumber">Primary Phone Number</Translate>
            </span>
          </dt>
          <dd>{contactDetailsEntity.primaryPhoneNumber}</dd>
          <dt>
            <span id="secondaryPhoneNumber">
              <Translate contentKey="medicalAidSystemApp.contactDetails.secondaryPhoneNumber">Secondary Phone Number</Translate>
            </span>
          </dt>
          <dd>{contactDetailsEntity.secondaryPhoneNumber}</dd>
          <dt>
            <span id="emailAddress">
              <Translate contentKey="medicalAidSystemApp.contactDetails.emailAddress">Email Address</Translate>
            </span>
          </dt>
          <dd>{contactDetailsEntity.emailAddress}</dd>
          <dt>
            <span id="physicalAddress">
              <Translate contentKey="medicalAidSystemApp.contactDetails.physicalAddress">Physical Address</Translate>
            </span>
          </dt>
          <dd>{contactDetailsEntity.physicalAddress}</dd>
          <dt>
            <span id="whatsappNumber">
              <Translate contentKey="medicalAidSystemApp.contactDetails.whatsappNumber">Whatsapp Number</Translate>
            </span>
          </dt>
          <dd>{contactDetailsEntity.whatsappNumber}</dd>
        </dl>
        <Button tag={Link} to="/contact-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact-details/${contactDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactDetailsDetail;
