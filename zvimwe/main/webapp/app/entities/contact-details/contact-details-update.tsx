import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactDetails } from 'app/shared/model/contact-details.model';
import { getEntity, updateEntity, createEntity, reset } from './contact-details.reducer';

export const ContactDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactDetailsEntity = useAppSelector(state => state.contactDetails.entity);
  const loading = useAppSelector(state => state.contactDetails.loading);
  const updating = useAppSelector(state => state.contactDetails.updating);
  const updateSuccess = useAppSelector(state => state.contactDetails.updateSuccess);

  const handleClose = () => {
    navigate('/contact-details');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...contactDetailsEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...contactDetailsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.contactDetails.home.createOrEditLabel" data-cy="ContactDetailsCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.contactDetails.home.createOrEditLabel">Create or edit a ContactDetails</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="contact-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.contactDetails.primaryPhoneNumber')}
                id="contact-details-primaryPhoneNumber"
                name="primaryPhoneNumber"
                data-cy="primaryPhoneNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: { value: /^[0][7][0-9]{8}$/, message: translate('entity.validation.pattern', { pattern: '^[0][7][0-9]{8}$' }) },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.contactDetails.secondaryPhoneNumber')}
                id="contact-details-secondaryPhoneNumber"
                name="secondaryPhoneNumber"
                data-cy="secondaryPhoneNumber"
                type="text"
                validate={{
                  pattern: { value: /^[0][7][0-9]{8}$/, message: translate('entity.validation.pattern', { pattern: '^[0][7][0-9]{8}$' }) },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.contactDetails.emailAddress')}
                id="contact-details-emailAddress"
                name="emailAddress"
                data-cy="emailAddress"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.contactDetails.physicalAddress')}
                id="contact-details-physicalAddress"
                name="physicalAddress"
                data-cy="physicalAddress"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.contactDetails.whatsappNumber')}
                id="contact-details-whatsappNumber"
                name="whatsappNumber"
                data-cy="whatsappNumber"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/contact-details" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ContactDetailsUpdate;
