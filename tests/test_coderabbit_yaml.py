"""
Comprehensive unit tests for CodeRabbit YAML configuration handling.
Testing framework: pytest (industry standard for Python testing)

This test suite covers:
- YAML configuration parsing and validation
- Schema validation for CodeRabbit configuration files  
- Error handling for malformed YAML
- Edge cases and boundary conditions
- Configuration key validation
- Default value handling
- Environment variable expansion
- File permission handling
- Unicode content support
"""
import pytest
import yaml
import os
import tempfile
from unittest.mock import patch, mock_open

class TestCodeRabbitYAMLConfiguration:
    """Test suite for CodeRabbit YAML configuration handling."""
    
    def setup_method(self):
        """Set up test fixtures before each test method."""
        self.valid_config = {
            'reviews': {
                'profile': 'chill',
                'request_changes_workflow': False,
                'high_level_summary': True,
                'poem': True,
                'review_status': True,
                'collapse_ellipsis': True,
                'auto_review': {
                    'enabled': True,
                    'drafts': False
                }
            },
            'chat': {
                'auto_reply': True
            },
            'knowledge_base': {
                'learnings': {
                    'scope': 'global'
                },
                'opt_out': False
            }
        }
        
        self.minimal_config = {
            'reviews': {
                'profile': 'assertive'
            }
        }
        
        self.empty_config = {}
        
    def teardown_method(self):
        """Clean up after each test method."""
        # Clean up any temporary files or state
        pass

    def test_parse_valid_complete_yaml_config(self):
        """Test parsing a complete valid CodeRabbit YAML configuration."""
        yaml_content = yaml.dump(self.valid_config)
        
        with patch('builtins.open', mock_open(read_data=yaml_content)):
            config = self._parse_config('.coderabbit.yaml')
            
        assert config['reviews']['profile'] == 'chill'
        assert config['reviews']['auto_review']['enabled'] is True
        assert config['reviews']['auto_review']['drafts'] is False
        assert config['chat']['auto_reply'] is True
        assert config['knowledge_base']['learnings']['scope'] == 'global'
        assert config['knowledge_base']['opt_out'] is False

    def test_parse_minimal_yaml_config(self):
        """Test parsing a minimal valid configuration with only required fields."""
        yaml_content = yaml.dump(self.minimal_config)
        
        with patch('builtins.open', mock_open(read_data=yaml_content)):
            config = self._parse_config('.coderabbit.yaml')
            
        assert config['reviews']['profile'] == 'assertive'
        assert len(config) == 1

    def test_parse_empty_yaml_file(self):
        """Test parsing an empty YAML file raises appropriate error."""
        with patch('builtins.open', mock_open(read_data='')), pytest.raises(ValueError, match="Empty or invalid configuration file"):
            self._parse_config('.coderabbit.yaml')

    def test_parse_yaml_with_null_content(self):
        """Test parsing YAML file with null content."""
        with patch('builtins.open', mock_open(read_data='null')), pytest.raises(ValueError, match="Empty or invalid configuration file"):
            self._parse_config('.coderabbit.yaml')

    def test_parse_malformed_yaml_syntax_error(self):
        """Test parsing malformed YAML content with syntax errors."""
        malformed_yaml = """
        reviews:
          profile: chill
          invalid_indentation:
        wrong_level: value
        """
        
        with patch('builtins.open', mock_open(read_data=malformed_yaml)), pytest.raises(yaml.YAMLError):
            self._parse_config('.coderabbit.yaml')

    def test_parse_yaml_with_tabs(self):
        """Test parsing YAML with tab characters (should fail)."""
        yaml_with_tabs = "reviews:\n\tprofile: chill"
        
        with patch('builtins.open', mock_open(read_data=yaml_with_tabs)), pytest.raises(yaml.YAMLError):
            self._parse_config('.coderabbit.yaml')

    def test_parse_nonexistent_file(self):
        """Test parsing a non-existent configuration file."""
        with patch('builtins.open', side_effect=FileNotFoundError("File not found")), pytest.raises(FileNotFoundError):
            self._parse_config('nonexistent.yaml')

    def test_validate_profile_values_success(self):
        """Test validation of valid profile configuration values."""
        valid_profiles = ['chill', 'assertive', 'enthusiastic']
        
        for profile in valid_profiles:
            config = {'reviews': {'profile': profile}}
            assert self._validate_config(config) is True

    def test_validate_profile_values_failure(self):
        """Test validation fails for invalid profile values."""
        invalid_profiles = ['invalid_profile', 'CHILL', 'Assertive', '', None, 123]
        
        for profile in invalid_profiles:
            config = {'reviews': {'profile': profile}}
            with pytest.raises(ValueError, match="Invalid profile"):
                self._validate_config(config)

    def test_validate_boolean_fields_success(self):
        """Test validation of boolean configuration fields with valid values."""
        boolean_fields = [
            ('reviews', 'request_changes_workflow'),
            ('reviews', 'high_level_summary'),
            ('reviews', 'poem'),
            ('reviews', 'review_status'),
            ('reviews', 'collapse_ellipsis'),
            ('chat', 'auto_reply'),
            ('knowledge_base', 'opt_out')
        ]
        
        for section, field in boolean_fields:
            # Test valid boolean values
            for value in [True, False]:
                config = {section: {field: value}}
                assert self._validate_config(config) is True

    def test_validate_boolean_fields_failure(self):
        """Test validation fails for invalid boolean values."""
        boolean_fields = [
            ('reviews', 'request_changes_workflow'),
            ('reviews', 'high_level_summary'),
            ('chat', 'auto_reply')
        ]
        
        invalid_boolean_values = ['true', 'false', 1, 0, 'yes', 'no', 'on', 'off', '']
        
        for section, field in boolean_fields:
            for invalid_value in invalid_boolean_values:
                config = {section: {field: invalid_value}}
                with pytest.raises(ValueError, match="must be boolean"):
                    self._validate_config(config)

    def test_validate_nested_auto_review_config_success(self):
        """Test validation of nested auto_review configuration with valid values."""
        valid_configs = [
            {
                'reviews': {
                    'auto_review': {
                        'enabled': True,
                        'drafts': False
                    }
                }
            },
            {
                'reviews': {
                    'auto_review': {
                        'enabled': False
                    }
                }
            },
            {
                'reviews': {
                    'auto_review': {
                        'drafts': True
                    }
                }
            }
        ]
        
        for config in valid_configs:
            assert self._validate_config(config) is True

    def test_validate_nested_auto_review_config_failure(self):
        """Test validation fails for invalid nested auto_review structure."""
        invalid_configs = [
            {
                'reviews': {
                    'auto_review': {
                        'enabled': 'invalid',
                        'drafts': True
                    }
                }
            },
            {
                'reviews': {
                    'auto_review': {
                        'enabled': True,
                        'drafts': 'yes'
                    }
                }
            },
            {
                'reviews': {
                    'auto_review': 'not_a_dict'
                }
            }
        ]
        
        for config in invalid_configs:
            with pytest.raises(ValueError):
                self._validate_config(config)

    def test_validate_knowledge_base_scope_success(self):
        """Test validation of valid knowledge base scope values."""
        valid_scopes = ['global', 'repository', 'organization']
        
        for scope in valid_scopes:
            config = {
                'knowledge_base': {
                    'learnings': {
                        'scope': scope
                    }
                }
            }
            assert self._validate_config(config) is True

    def test_validate_knowledge_base_scope_failure(self):
        """Test validation fails for invalid knowledge base scope values."""
        invalid_scopes = ['invalid_scope', 'GLOBAL', 'Repository', '', None, 123]
        
        for scope in invalid_scopes:
            config = {
                'knowledge_base': {
                    'learnings': {
                        'scope': scope
                    }
                }
            }
            with pytest.raises(ValueError, match="Invalid scope"):
                self._validate_config(config)

    def test_config_with_unknown_fields_permissive(self):
        """Test handling of unknown configuration fields in permissive mode."""
        config_with_unknown = {
            'reviews': {
                'profile': 'chill',
                'unknown_field': 'value'
            },
            'unknown_section': {
                'field': 'value'
            }
        }
        
        # Should ignore unknown fields in permissive mode
        result = self._validate_config(config_with_unknown, strict=False)
        assert result is True

    def test_config_with_unknown_fields_strict(self):
        """Test handling of unknown configuration fields in strict mode."""
        config_with_unknown = {
            'reviews': {
                'profile': 'chill',
                'unknown_field': 'value'
            },
            'unknown_section': {
                'field': 'value'
            }
        }
        
        # Should raise error in strict mode
        with pytest.raises(ValueError, match="Unknown field"):
            self._validate_config(config_with_unknown, strict=True)

    def test_merge_with_defaults_partial_config(self):
        """Test merging partial user configuration with default values."""
        user_config = {
            'reviews': {
                'profile': 'assertive',
                'poem': False
            }
        }
        
        merged_config = self._merge_with_defaults(user_config)
        
        # Should preserve user-specified values
        assert merged_config['reviews']['profile'] == 'assertive'
        assert merged_config['reviews']['poem'] is False
        
        # Should have default values for unspecified fields
        assert 'request_changes_workflow' in merged_config['reviews']
        assert 'auto_review' in merged_config['reviews']
        assert merged_config['reviews']['auto_review']['enabled'] is True

    def test_merge_with_defaults_empty_config(self):
        """Test merging empty configuration returns all defaults."""
        merged_config = self._merge_with_defaults({})
        
        assert merged_config['reviews']['profile'] == 'chill'
        assert merged_config['reviews']['auto_review']['enabled'] is True
        assert merged_config['chat']['auto_reply'] is True
        assert merged_config['knowledge_base']['opt_out'] is False

    def test_config_serialization_roundtrip(self):
        """Test serializing configuration to YAML and parsing it back."""
        config = self.valid_config
        yaml_output = self._serialize_config(config)
        
        # Should be valid YAML that parses back to same config
        reparsed_config = yaml.safe_load(yaml_output)
        assert reparsed_config == config

    def test_config_serialization_formatting(self):
        """Test YAML serialization produces properly formatted output."""
        config = self.valid_config
        yaml_output = self._serialize_config(config)
        
        # Should not use flow style (inline format)
        assert '{' not in yaml_output
        assert '[' not in yaml_output
        
        # Should have proper indentation
        lines = yaml_output.split('\n')
        indented_lines = [line for line in lines if line.startswith('  ')]
        assert len(indented_lines) > 0

    def test_config_file_permissions_readable(self):
        """Test handling of configuration files with read permissions."""
        with tempfile.NamedTemporaryFile(mode='w', suffix='.yaml', delete=False) as f:
            yaml.dump(self.valid_config, f)
            temp_file = f.name
            
        try:
            # Test readable file
            os.chmod(temp_file, 0o644)
            config = self._parse_config(temp_file)
            assert config is not None
            assert config['reviews']['profile'] == 'chill'
                
        finally:
            os.chmod(temp_file, 0o644)  # Restore permissions for cleanup
            os.unlink(temp_file)

    def test_config_file_permissions_unreadable(self):
        """Test handling of configuration files without read permissions."""
        with tempfile.NamedTemporaryFile(mode='w', suffix='.yaml', delete=False) as f:
            yaml.dump(self.valid_config, f)
            temp_file = f.name
            
        try:
            # Test unreadable file (skip on Windows as it doesn't support this)
            if os.name != 'nt':
                os.chmod(temp_file, 0o000)
                with pytest.raises(PermissionError):
                    self._parse_config(temp_file)
                
        finally:
            os.chmod(temp_file, 0o644)  # Restore permissions for cleanup
            os.unlink(temp_file)

    def test_config_with_yaml_comments(self):
        """Test parsing YAML configuration with comments."""
        yaml_with_comments = """
        # CodeRabbit configuration file
        reviews:
          profile: chill  # Use chill profile for gentler reviews
          auto_review:
            enabled: true  # Enable automatic reviews
            drafts: false  # Skip draft PRs
        # Chat configuration  
        chat:
          auto_reply: true  # Automatically reply to chat messages
        """
        
        with patch('builtins.open', mock_open(read_data=yaml_with_comments)):
            config = self._parse_config('.coderabbit.yaml')
            
        assert config['reviews']['profile'] == 'chill'
        assert config['reviews']['auto_review']['enabled'] is True
        assert config['reviews']['auto_review']['drafts'] is False
        assert config['chat']['auto_reply'] is True

    def test_config_with_environment_variables(self):
        """Test configuration that uses environment variable substitution."""
        yaml_with_env = """
        reviews:
          profile: ${CODERABBIT_PROFILE:-chill}
        chat:
          auto_reply: ${CODERABBIT_AUTO_REPLY:-true}
        knowledge_base:
          opt_out: ${CODERABBIT_KNOWLEDGE_BASE_OPT_OUT:-false}
        """
        
        env_vars = {
            'CODERABBIT_PROFILE': 'assertive',
            'CODERABBIT_AUTO_REPLY': 'false'
            # CODERABBIT_KNOWLEDGE_BASE_OPT_OUT not set, should use default
        }
        
        with patch.dict(os.environ, env_vars), patch('builtins.open', mock_open(read_data=yaml_with_env)):
            config = self._parse_config('.coderabbit.yaml', expand_env_vars=True)
            
        assert config['reviews']['profile'] == 'assertive'
        assert config['chat']['auto_reply'] is False
        assert config['knowledge_base']['opt_out'] is False

    @pytest.mark.parametrize("invalid_yaml", [
        "invalid: yaml: content:",
        "- list\n  without:\n   proper: indentation",
        "{ invalid: json, like: syntax, }",
        "reviews:\n  profile",  # Missing value
        "reviews profile chill",  # Missing colons
        "reviews:\nprofile: chill\n  extra: indented",  # Invalid indentation
    ])
    def test_various_malformed_yaml_formats(self, invalid_yaml):
        """Test parsing various types of malformed YAML raises appropriate errors."""
        with patch('builtins.open', mock_open(read_data=invalid_yaml)), pytest.raises((yaml.YAMLError, ValueError)):
            self._parse_config('.coderabbit.yaml')

    def test_large_configuration_file(self):
        """Test parsing a large configuration file performs adequately."""
        large_config = self.valid_config.copy()
        
        # Add many additional sections to create a large config
        for i in range(50):
            large_config[f'custom_section_{i}'] = {
                f'field_{j}': f'value_{j}' for j in range(20)
            }
            
        yaml_content = yaml.dump(large_config)
        
        with patch('builtins.open', mock_open(read_data=yaml_content)):
            config = self._parse_config('.coderabbit.yaml')
            
        assert len(config) > 50
        assert config['reviews']['profile'] == 'chill'
        assert 'custom_section_0' in config
        assert config['custom_section_0']['field_0'] == 'value_0'

    def test_unicode_content_handling(self):
        """Test handling of Unicode content in configuration."""
        unicode_config = {
            'reviews': {
                'profile': 'chill',
                'custom_message': 'Hello 世界! 🤖 CodeRabbit rocks! 🚀'
            },
            'chat': {
                'greeting': 'Bonjour! 🇫🇷'
            }
        }
        
        yaml_content = yaml.dump(unicode_config, allow_unicode=True)
        
        with patch('builtins.open', mock_open(read_data=yaml_content)):
            config = self._parse_config('.coderabbit.yaml')
            
        assert config['reviews']['custom_message'] == 'Hello 世界! 🤖 CodeRabbit rocks! 🚀'
        assert config['chat']['greeting'] == 'Bonjour! 🇫🇷'

    def test_deeply_nested_configuration(self):
        """Test handling of deeply nested configuration structures."""
        deep_config = {
            'reviews': {
                'auto_review': {
                    'patterns': {
                        'include': {
                            'paths': ['src/**/*.py', 'tests/**/*.py'],
                            'extensions': ['.py', '.js', '.ts']
                        },
                        'exclude': {
                            'paths': ['build/**', 'dist/**'],
                            'patterns': ['*.min.js', '*.bundle.*']
                        }
                    }
                }
            }
        }
        
        yaml_content = yaml.dump(deep_config)
        
        with patch('builtins.open', mock_open(read_data=yaml_content)):
            config = self._parse_config('.coderabbit.yaml')
            
        assert config['reviews']['auto_review']['patterns']['include']['paths'][0] == 'src/**/*.py'
        assert '.py' in config['reviews']['auto_review']['patterns']['include']['extensions']
        assert 'build/**' in config['reviews']['auto_review']['patterns']['exclude']['paths']

    def test_config_validation_with_missing_required_sections(self):
        """Test validation fails when required sections are missing."""
        configs_missing_required = [
            {},  # Completely empty
            {'chat': {'auto_reply': True}},  # Missing reviews section
            {'reviews': {}},  # Reviews section exists but is empty
        ]
        
        for config in configs_missing_required:
            with pytest.raises(ValueError, match="required"):
                self._validate_config(config, require_reviews=True)

    def test_config_with_numeric_and_string_mixing(self):
        """Test configuration with mixed numeric and string values."""
        mixed_config = {
            'reviews': {
                'profile': 'chill',
                'max_comments': 10,
                'timeout_seconds': 30.5
            },
            'api': {
                'version': '1.2.3',
                'port': 8080
            }
        }
        
        yaml_content = yaml.dump(mixed_config)
        
        with patch('builtins.open', mock_open(read_data=yaml_content)):
            config = self._parse_config('.coderabbit.yaml')
            
        assert config['reviews']['profile'] == 'chill'
        assert config['reviews']['max_comments'] == 10
        assert config['reviews']['timeout_seconds'] == 30.5
        assert config['api']['version'] == '1.2.3'
        assert config['api']['port'] == 8080

    # Helper methods that would be part of the actual coderabbit_yaml module
    def _parse_config(self, file_path, expand_env_vars=False):
        """Helper method to parse CodeRabbit YAML configuration."""
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
            
        if not content.strip():
            raise ValueError("Empty or invalid configuration file")
            
        config = yaml.safe_load(content)
        
        if config is None:
            raise ValueError("Empty or invalid configuration file")
        
        if expand_env_vars:
            config = self._expand_environment_variables(config)
            
        return config

    def _validate_config(self, config, strict=True, require_reviews=False):
        """Helper method to validate CodeRabbit configuration."""
        if not isinstance(config, dict):
            raise ValueError("Configuration must be a dictionary")
        
        if require_reviews and 'reviews' not in config:
            raise ValueError("reviews section is required")
            
        # Validate profile
        if 'reviews' in config and 'profile' in config['reviews']:
            profile = config['reviews']['profile']
            valid_profiles = ['chill', 'assertive', 'enthusiastic']
            if profile not in valid_profiles:
                raise ValueError(f"Invalid profile: {profile}. Must be one of {valid_profiles}")
                
        # Validate boolean fields
        boolean_fields = [
            ('reviews', 'request_changes_workflow'),
            ('reviews', 'high_level_summary'),
            ('reviews', 'poem'),
            ('reviews', 'review_status'),
            ('reviews', 'collapse_ellipsis'),
            ('chat', 'auto_reply'),
            ('knowledge_base', 'opt_out')
        ]
        
        for section, field in boolean_fields:
            if section in config and field in config[section]:
                value = config[section][field]
                if not isinstance(value, bool):
                    raise ValueError(f"{section}.{field} must be boolean")
                    
        # Validate nested auto_review
        if ('reviews' in config and 'auto_review' in config['reviews']):
            auto_review = config['reviews']['auto_review']
            if not isinstance(auto_review, dict):
                raise ValueError("auto_review must be a dictionary")
                
            for field in ['enabled', 'drafts']:
                if field in auto_review and not isinstance(auto_review[field], bool):
                    raise ValueError(f"auto_review.{field} must be boolean")
                    
        # Validate knowledge base scope
        if ('knowledge_base' in config and 
            'learnings' in config['knowledge_base'] and 
            'scope' in config['knowledge_base']['learnings']):
            
            scope = config['knowledge_base']['learnings']['scope']
            valid_scopes = ['global', 'repository', 'organization']
            if scope not in valid_scopes:
                raise ValueError(f"Invalid scope: {scope}. Must be one of {valid_scopes}")
                
        # Check for unknown fields in strict mode
        if strict:
            known_sections = ['reviews', 'chat', 'knowledge_base']
            for section in config:
                if section not in known_sections:
                    raise ValueError(f"Unknown field: {section}")
                    
        return True

    def _merge_with_defaults(self, user_config):
        """Helper method to merge user config with defaults."""
        defaults = {
            'reviews': {
                'profile': 'chill',
                'request_changes_workflow': False,
                'high_level_summary': True,
                'poem': True,
                'review_status': True,
                'collapse_ellipsis': True,
                'auto_review': {
                    'enabled': True,
                    'drafts': False
                }
            },
            'chat': {
                'auto_reply': True
            },
            'knowledge_base': {
                'learnings': {
                    'scope': 'global'
                },
                'opt_out': False
            }
        }
        
        def deep_merge(base, override):
            """Recursively merge dictionaries."""
            result = base.copy()
            for key, value in override.items():
                if key in result and isinstance(result[key], dict) and isinstance(value, dict):
                    result[key] = deep_merge(result[key], value)
                else:
                    result[key] = value
            return result
            
        return deep_merge(defaults, user_config)

    def _serialize_config(self, config):
        """Helper method to serialize configuration to YAML."""
        return yaml.dump(config, 
                        default_flow_style=False, 
                        allow_unicode=True,
                        sort_keys=True,
                        indent=2)

    def _expand_environment_variables(self, config):
        """Helper method to expand environment variables in configuration."""
        import re
        
        def expand_value(value):
            if isinstance(value, str):
                # Expand ${VAR:-default} pattern
                pattern = r'\$\{([^}]+)\}'
                matches = re.findall(pattern, value)
                for match in matches:
                    if ':-' in match:
                        var_name, default_value = match.split(':-', 1)
                        env_value = os.environ.get(var_name, default_value)
                    else:
                        env_value = os.environ.get(match, '')
                    value = value.replace(f'${{{match}}}', env_value)
                
                # Convert string booleans to actual booleans
                if value.lower() in ['true', 'false']:
                    return value.lower() == 'true'
                    
            elif isinstance(value, dict):
                return {k: expand_value(v) for k, v in value.items()}
            elif isinstance(value, list):
                return [expand_value(item) for item in value]
                
            return value
        
        return expand_value(config)

if __name__ == '__main__':
    pytest.main([__file__, '-v'])